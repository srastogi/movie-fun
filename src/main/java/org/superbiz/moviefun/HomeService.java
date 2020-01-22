package org.superbiz.moviefun;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.superbiz.moviefun.albums.Album;
import org.superbiz.moviefun.albums.AlbumFixtures;
import org.superbiz.moviefun.albums.AlbumsBean;
import org.superbiz.moviefun.movies.Movie;
import org.superbiz.moviefun.movies.MovieFixtures;
import org.superbiz.moviefun.movies.MoviesBean;

@Component
public class HomeService {

    private final MoviesBean moviesBean;
    private final AlbumsBean albumsBean;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeService(MoviesBean moviesBean, AlbumsBean albumsBean, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesBean = moviesBean;
        this.albumsBean = albumsBean;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @Transactional(transactionManager = "albumsTransactionManager")
    public void loadAlbums() {
        for (Album album : albumFixtures.load()) {
            albumsBean.addAlbum(album);
        }
    }

    @Transactional(transactionManager = "moviesTransactionManager")
    public void loadMovies() {
        for (Movie movie : movieFixtures.load()) {
            moviesBean.addMovie(movie);
        }
    }
}
