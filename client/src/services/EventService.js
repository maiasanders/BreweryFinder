import axios from "axios";

export default {
    getFeaturedEvents() {
        return axios.get('/events/featured')
    },

    getEventsByBrewery(id) {
        return axios.get(`/breweries/${id}/events`)
    },

    getAllEvents() {
        return axios.get('/events')
    },

    getEventById(id) {
        return axios.get(`/events/${id}`)
    },

    postEvent(event) {
        return axios.post('/events', event)
    },

    updateEvent(id, event) {
        return axios.put(`/events/${id}`, event)
    },
    
    deleteEvent(id) {
        return axios.delete(`/events/${id}`)
    }
}