import axios from "axios";

export default {

getAllBreweries() {
    return axios.get('/breweries');
},

getBrewery(id) {
    return axios.get(`/breweries/${id}`);
},

getBreweriesByBrewer() {
    return axios.get(`/breweries/byuser`);
},

createBrewery(brewery) {
    return axios.post(`/breweries`, brewery);
},

updateBrewery(id, brewery) {
    return axios.put(`/breweries/${id}`, brewery)
}

}