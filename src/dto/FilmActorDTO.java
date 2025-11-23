package dto;

public class FilmActorDTO {
    private int filmId;
    private int actorId;
    
    public FilmActorDTO() {}
    
    public FilmActorDTO(int filmId, int actorId) {
        this.filmId = filmId;
        this.actorId = actorId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Override
    public String toString() {
        return "FilmActorDTO [filmId=" + filmId + ", actorId=" + actorId + "]";
    }
}
