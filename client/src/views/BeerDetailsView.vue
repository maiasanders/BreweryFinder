<template>
<div id="beer-details">
    <img :src="beer.imgUrl || 'https://placehold.co/600x400/darkgray/white'">
    <div id="info-reviews">
    </div>
    <BeerDetails :beer="beer" />
    <BeerReviews :beer-id="beer.beerId" />
</div>
</template>

<script>
import BeerService from '../services/BeerService';

import BeerDetails from '../components/BeerDetails.vue';
import BeerReviews from '../components/BeerReviews.vue';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    components: {
        BeerDetails,
        BeerReviews
    },
    data() {
        return {
            beer: {},
            isLoading: true,
            displayMessage: null
        }
    },
    methods: {
        loadBeer() {
            BeerService.getBeer(this.$route.params.id)
                .then(response => {
                    this.beer = response.data;
                    this.isLoading = false
                })
                .catch(error => {
                    const status = axiosErrorHandling(error)
                    if (status === 404) this.$router.push({ name: 'pageNotFound' })
                })
        },
        errorHandling(error) {

        }

    },
    created() {
        this.loadBeer();
    }
}
</script>

<style scoped>
#beer-details {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "pic details" "pic reviews";
    gap: 20px;
}

img {
    left: 10px;
    grid-area: pic;
    position: fixed;
    top: 120px;
    height: calc(100vh - 155px);
    width: 48%;
    object-fit: cover;
    bottom: 30px;
}

/* #info-reviews {
    grid-area: details;
} */

#beer-info {
    grid-area: details;
    padding-right: 10px;
}

#beer-reviews {
    grid-area: reviews;
    margin-right: 10px;
}

@media only screen and (max-width: 425px) {
    #beer-details {
        grid-template-columns: 1fr;
        grid-template-areas: "pic" "details" "reviews";
        margin: 10px;
    }
    img {
        position: static;
        height: auto;
        width: 95%;
        margin: 10px;
    }
}
</style>