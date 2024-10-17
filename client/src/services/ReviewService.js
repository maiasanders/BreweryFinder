import axios from "axios";

export default {
    getReviews(id) {
        return axios.get(`/beers/${id}/reviews`)
    },

    postReview(id, review) {
        return axios.post(`/beers/${id}/reviews/`, review);
    }
}