package com.spotifyinfo.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifyinfo.domain.SpotifyAccessTokenResponse;
import com.spotifyinfo.domain.SpotifyAuthorizationCodeUriResponse;
import com.spotifyinfo.domain.SpotifyPaging;
import com.spotifyinfo.domain.SpotifySimplifiedPlaylist;
import com.spotifyinfo.utils.JsonConverterUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.spotifyinfo.constants.SpotifyPermissionsConstants.READ_PERMISSIONS;

@Data
@Component
public class SpotifyClient {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String baseUrl;
    private String authorizationBaseUrl;
    private HttpClient httpClient;

    public SpotifyClient(
            @Value("${spotify.client-id}") String clientId,
            @Value("${spotify.client-secret}") String clientSecret,
            @Value("${spotify.redirect-uri}") String redirectUri,
            @Value("${spotify.base-url}") String baseUrl,
            @Value("${spotify.authorization-base-url}") String authorizationBaseUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.baseUrl = baseUrl;
        this.authorizationBaseUrl = authorizationBaseUrl;
        this.httpClient = new HttpClient();
    }

    @PostConstruct
    private void init() {
        if (StringUtils.isBlank(this.clientId)) {
            throw new RuntimeException("Client id is not present in configuration!");
        }

        if (StringUtils.isBlank(this.clientSecret)) {
            throw new RuntimeException("Client secret is not present in configuration!");
        }

        if (StringUtils.isBlank(this.redirectUri)) {
            throw new RuntimeException("Redirect Uri is not present in configuration!");
        }
    }

    public SpotifyAccessTokenResponse getClientCredentials() {
        String url = this.authorizationBaseUrl + "/api/token";
        String credentials = clientId + ":" + clientSecret;

        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        SpotifyHeaders<String, String> headers = new SpotifyHeaders<>();
        headers.put("Authorization", "Basic " + base64Credentials);

        Map<String, String> formBody = new HashMap<>();
        formBody.put("grant_type", "client_credentials");

        String accessTokenResponse = this.httpClient.post(url, formBody, headers);
        return JsonConverterUtil.jsonToObject(accessTokenResponse, SpotifyAccessTokenResponse.class);
    }

    public SpotifyAuthorizationCodeUriResponse getAuthorizationCodeURI() {
        String url = this.authorizationBaseUrl + "/authorize";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("response_type", "code");
        queryParams.put("client_id", this.clientId);
        queryParams.put("scope", READ_PERMISSIONS);
        queryParams.put("redirect_uri", this.redirectUri);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        String uri = urlBuilder.build().toString();
        SpotifyAuthorizationCodeUriResponse spotifyAuthorizationCodeUriResponse = new SpotifyAuthorizationCodeUriResponse();
        try {
            spotifyAuthorizationCodeUriResponse.setUri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return spotifyAuthorizationCodeUriResponse;
    }

    public SpotifyAccessTokenResponse getAuthorizationCode(String code) {
        String url = this.authorizationBaseUrl + "/api/token";
        String credentials = clientId + ":" + clientSecret;

        Map<String, String> formBody = new HashMap<>();
        formBody.put("code", code);
        formBody.put("redirect_uri", this.redirectUri);
        formBody.put("grant_type", "authorization_code");

        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        SpotifyHeaders<String, String> headers = new SpotifyHeaders<>();
        headers.put("Authorization", "Basic " + base64Credentials);
        headers.put("content-type", "application/x-www-form-urlencoded");

        String authorizationCodeResponse = this.httpClient.post(url, formBody, headers);
        return JsonConverterUtil.jsonToObject(authorizationCodeResponse, SpotifyAccessTokenResponse.class);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SpotifyPaging<SpotifySimplifiedPlaylist> getListOfCurrentUsersPlaylists(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access token can't be null");
        }
        String url = this.baseUrl + "/v1/me/playlists";
        SpotifyHeaders<String, String> headers = new SpotifyHeaders<>();
        headers.put("Authorization", "Bearer " + accessToken);

        String playlists = this.httpClient.get(url, null, headers);
        if (StringUtils.isBlank(playlists)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Playlists are null");
        }

        JSONObject jsonObject = new JSONObject(playlists);
        JSONArray itemsArray = jsonObject.has("items") && Objects.nonNull(jsonObject.get("items")) ? jsonObject.getJSONArray("items") : null;
        List<SpotifySimplifiedPlaylist> playlistList;

        if (itemsArray == null) {
            playlistList = new ArrayList<>();
        } else {
            playlistList = JsonConverterUtil.convertList(itemsArray.toString(), SpotifySimplifiedPlaylist.class);
        }
        SpotifyPaging<SpotifySimplifiedPlaylist> spotifySimplifiedPlaylistSpotifyPaging = new SpotifyPaging<>();

        spotifySimplifiedPlaylistSpotifyPaging.setNext(jsonObject.has("next") && !jsonObject.isNull("next") ? jsonObject.getString("next") : null);
        spotifySimplifiedPlaylistSpotifyPaging.setOffset(jsonObject.has("offset") && !jsonObject.isNull("offset") ? jsonObject.getInt("offset") : 0);
        spotifySimplifiedPlaylistSpotifyPaging.setLimit(jsonObject.has("limit") && !jsonObject.isNull("limit") ? jsonObject.getInt("limit") : 0);
        spotifySimplifiedPlaylistSpotifyPaging.setPrevious(jsonObject.has("previous") && !jsonObject.isNull("previous") ? jsonObject.getString("previous") : null);
        spotifySimplifiedPlaylistSpotifyPaging.setHref(jsonObject.has("href") && !jsonObject.isNull("href") ? jsonObject.getString("href") : null);
        spotifySimplifiedPlaylistSpotifyPaging.setTotal(jsonObject.has("total") && !jsonObject.isNull("total") ? jsonObject.getInt("total") : 0);
        spotifySimplifiedPlaylistSpotifyPaging.setItems(playlistList.toArray(new SpotifySimplifiedPlaylist[]{}));

        return spotifySimplifiedPlaylistSpotifyPaging;
    }
}
