package com.spotifyinfo.client;

import com.spotifyinfo.domain.AccessTokenResponseDTO;
import com.spotifyinfo.domain.AuthorizationCodeUriResponseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import static com.spotifyinfo.constants.SpotifyPermissionsConstants.READ_PERMISSIONS;

@Data
public class SpotifyClient {
    private String clientId;
    private String clientSecret;
    private URI redirectUri;
    private SpotifyApi spotifyApi;
    private String accessToken;
    private String refreshToken;

    public SpotifyClient(SpotifyClientConfig config) {
        if (StringUtils.isNotBlank(config.getClientId())) {
            this.clientId = config.getClientId();
        }

        if (StringUtils.isNotBlank(config.getClientSecret())) {
            this.clientSecret = config.getClientSecret();
        }

        if (Objects.nonNull(config.getRedirectUri())) {
            this.redirectUri = config.getRedirectUri();
        }

        this.accessToken = config.getAccessToken();
        this.refreshToken = config.getRefreshToken();

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .setAccessToken(config.getAccessToken())
                .setRefreshToken(config.getRefreshToken())
                .build();
    }

    public AccessTokenResponseDTO getClientCredentials() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            //TODO: logar tempo que expira
            return AccessTokenResponseDTO.builder()
                    .accessToken(clientCredentials.getAccessToken())
                    .tokenType(clientCredentials.getTokenType())
                    .expiresIn(clientCredentials.getExpiresIn())
                    .build();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            //TODO: logar erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO: remover DTO do client
    public AuthorizationCodeUriResponseDTO getAuthorizationCodeURI() {
        try {
            //TODO: Add more permissions and move these to an appropriate place
            AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                    .scope(READ_PERMISSIONS).build();
            URI uri = authorizationCodeUriRequest.execute();

            if (uri == null) {
                throw new RuntimeException("The returned URI is null!");
            }

            return new AuthorizationCodeUriResponseDTO(uri);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
            //        System.out.println("URI gerada: " + uri.toString());
            //TODO: Logger
        }
    }

    public AccessTokenResponseDTO getAuthorizationCode(String code) {
        try {
            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                    .build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());


            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            return AccessTokenResponseDTO.builder()
                    .accessToken(authorizationCodeCredentials.getAccessToken())
                    .refreshToken(authorizationCodeCredentials.getRefreshToken())
                    .expiresIn(authorizationCodeCredentials.getExpiresIn())
                    .tokenType(authorizationCodeCredentials.getTokenType())
                    .build();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Paging<PlaylistSimplified> getListOfCurrentUsersPlaylists() {
        try {
            GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                    .getListOfCurrentUsersPlaylists()
                    .build();

            //TODO: Logger
//            System.out.println("Total: " + getListOfCurrentUsersPlaylistsRequest.execute().getTotal());
            return getListOfCurrentUsersPlaylistsRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
