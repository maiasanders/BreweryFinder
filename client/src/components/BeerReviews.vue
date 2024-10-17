<template>
    <div id="beer-reviews">
        <div id="reviews-header">
            <h3>Reviews</h3>
            <router-link :to="{ name: 'reviewForm', params: { id: $route.params.id } }">Leave a review!</router-link>
        </div>
        <p v-if="reviews.length === 0">Be the first to review this beer!</p>
        <div id="reviews" v-else>
            <div id="averages">
                <div id="average-stars">
                    <font-awesome-icon icon="fa-solid fa-star" v-for="(star, ind) in averageRating" :key="ind"/>
                    <font-awesome-icon icon="fa-regular fa-star" v-for="(star, ind) in (5 - averageRating)" :key="ind" />
                </div>
                <h4>{{ avgRecommend * 100 }}% Recommend</h4>
            </div>
            <div class="review" v-for="review in reviews" :key="review.id">
                <div class="review-head">
                    <h4>{{ review.user }}</h4>
                    <div class="star-rating">
                        <font-awesome-icon icon="fa-solid fa-star" v-for="(star, index) in review.rating" :key="index"/>
                        <font-awesome-icon icon="fa-regular fa-star" v-for="(star, ind) in (5 - review.rating)" :key="ind"/>
                    </div>
                    <font-awesome-icon class="recommended" icon="fa-regular fa-thumbs-up" v-if="review.recommended" />
                    <font-awesome-icon class="recommended" icon="fa-regular fa-thumbs-down" v-else />
                </div>
                <p>{{ review.reviewText }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import ReviewService from '../services/ReviewService'

export default {
    data() {
        return {
            reviews: []
        }
    },
    methods: {
        getReviews() {
            ReviewService.getReviews(this.$route.params.id)
                .then(response => {
                    this.reviews = response.data;
                })

        }
    },
    computed: {
        totalReviews() {
            return this.reviews.length
        },
        averageRating() {
            let grossTotal = this.reviews.map(review => review.rating).reduce((a, c) => a + c, 0) / this.reviews.length;
            return grossTotal;
        },
        avgRecommend() {
            let totalRecommended = this.reviews
                .map(review => review.recommended)
                .reduce((a, c) => a + c, 0)
            return totalRecommended / (this.reviews.length);
        }
    },
    created() {
        this.getReviews();
    }
}
</script>

<style scoped>
#beer-reviews {
    background-color: var(--card-color);
    border: 1px solid var(--input-border-color);
    /* padding: 10px; */
    border-radius: 10px;
}

#reviews-header {
    background-color: var(--nav-color);
    border-radius: 10px 10px 0 0;
    padding: 10px;
}

#reviews {
    padding: 10px;
}

.review {
    padding: 10px 0 10px 0;
    border-bottom: 1px solid var(--input-border-color);
    /* display: grid; */
    /* grid-template-columns: 1fr 1fr;
    grid-template-areas: "user user" "star reco" "txt txt"; */
    &:last-child {
        border: none;
    }
}
.review-head {
    display: flex;
    justify-content: end;
    gap: 10px
}
.review-head h4 {
    margin-right: auto;
}

#averages {
    display: flex;
    justify-content: space-between;
}
#averages svg {
    font-size: 1.5em;
}

svg {
    cursor: default;
    &:hover {
        text-shadow: none;
        font-size: 1em;
    }
}

</style>