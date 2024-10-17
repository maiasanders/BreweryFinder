import axios from "axios";

export default {
    getCategories() {
        return axios.get('/categories')
    },

    addCategory(cat) {
        return axios.post('/categories', cat)
    }
}