<template>
    <div id="review-form-view" class="page-content">
        <h1>{{ beer.beerName }}</h1>
        <h3>{{ beer.breweryName }}</h3>
        <ReviewForm />
    </div>
</template>

<script>
import ReviewForm from '../components/ReviewForm.vue';

import BeerService from '../services/BeerService';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    components: {
        ReviewForm
    },
    data() {
        return {
            beer: { }
        }
    },
    created() {
        this.$store.commit('HIDE_NAV')
        BeerService.getBeer(this.$route.params.id)
            .then(response => {
                this.beer = response.data
            })
            .catch(error => axiosErrorHandling(error))
    }
}
</script>

<style scoped>
h1, h3 {
    text-align: center;
    margin-bottom: 1rem;
}
</style>