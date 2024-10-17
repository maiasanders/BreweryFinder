import axios from "axios";

export default {
    getAllBeers() {
        return axios.get('/beers')
    },

    getByBrewery(id) {
        return axios.get(`/breweries/${id}/beers`);
    },

    getBeer(id) {
        return axios.get(`beers/${id}`);
    },

    getStyles() {
        return axios.get('/styles');
    },

    postBeer(beer) {
        return axios.post('/beers', beer)
    },

    updateBeer(id, beer) {
        return axios.put(`/beers/${id}`, beer)
    },

    deleteBeer(id) {
        return axios.delete(`/beers/${id}`)
    }
}