<template>
<form id="review-form" @submit.prevent="postReview">
    <div id="star-rating">
        <font-awesome-icon icon="fa-regular fa-star" v-if="review.rating < 1" @click="updateRating(1)" />
        <font-awesome-icon icon="fa-solid fa-star" @click="updateRating(1)" v-else />
        <font-awesome-icon icon="fa-regular fa-star" v-if="review.rating < 2" @click="updateRating(2)" />
        <font-awesome-icon icon="fa-solid fa-star" @click="updateRating(2)" v-else />
        <font-awesome-icon icon="fa-regular fa-star" v-if="review.rating < 3" @click="updateRating(3)" />
        <font-awesome-icon icon="fa-solid fa-star" @click="updateRating(3)" v-else />
        <font-awesome-icon icon="fa-regular fa-star" v-if="review.rating < 4" @click="updateRating(4)" />
        <font-awesome-icon icon="fa-solid fa-star" v-else @click="updateRating(4)" />
        <font-awesome-icon icon="fa-regular fa-star" v-if="review.rating < 5" @click="updateRating(5)" />
        <font-awesome-icon icon="fa-solid fa-star" v-else />
    </div>
    <font-awesome-icon id="recommended" icon="fa-regular fa-thumbs-up" v-if="review.recommended" @click.prevent="review.recommended = false" />
    <font-awesome-icon id="recommended" icon="fa-regular fa-thumbs-down" v-else @click.prevent="toggleRecommend" />
    <div class="textarea-sec">
        <label for="review-text">Review</label>
        <textarea name="review-text" id="review-text" v-model="review.reviewText" placeholder="Enter your review here!"></textarea>
    </div>
    <input type="submit">
</form>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import ReviewService from '../services/ReviewService';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    data() {
        return {
            review: {
                rating: 0,
                recommended: false,
                reviewText: "",
                beerId: this.$route.params.id
            }
        };
    },
    methods: {
        toggleRecommend() {
            this.review.recommended = !this.recommended;
        },
        updateRating(int) {
            this.review.rating = int;
        },
        postReview() {
            
            ReviewService.postReview(this.$route.params.id, this.review)
                .then(response => {
                    if (response.status === 201) {
                        this.$router.push({ name: 'beer', params: { id: this.beerId } })
                    }
                })
                .catch(error => axiosErrorHandling(error))
        }
    },
    components: { FontAwesomeIcon }
}
</script>

<style scoped>
#star-rating *, #recommended {
    font-size: 2rem;
    &:hover {
        font-size: 2.1rem;
        text-shadow: 0 0 2px white;
    }
}
@media only screen and (max-width: 425px) {
    #review-form {
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-areas: "stars stars" "txt txt" "reco sub";
    }
    #star-rating {
        grid-area: stars;
        display: flex;
        justify-content: space-around;
    }
    .textarea-sec {
        grid-area: txt;
    }
    #recommended {
        grid-area: reco;
    }
    input[type=submit] {
        grid-area: sub;
    }

}
</style>