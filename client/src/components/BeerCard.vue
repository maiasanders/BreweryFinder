<template>
<div class="beer-card card" @click="$router.push({name: 'beer', params: {id: beer.beerId}})">
    <h3>{{ beer.beerName }}</h3>
    
    <img :src="beer.imgUrl" >
    <div class="card-foot">
        <font-awesome-icon icon="fa-regular fa-comment" @click.stop="$router.push({name: 'reviewForm', params: {id: beer.beerId}})" />
        <h4 class="brew-name" v-if="showBrewery">{{ beer.breweryName }}</h4>
    </div>
</div>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faComment } from '@fortawesome/free-regular-svg-icons'

export default {
    components: [faComment],
    props: {
        beer: {
            type: Object,
            required: true
        },
        showBrewery: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style scoped>
.card-foot {
    display: flex;
    justify-content: space-between;
}
h4 {
    text-align: end;
}
.beer-card {
    border: 1px solid #B5B185;
    border-radius: 10px;
    padding: 10px;
    background-color: #f2f1e9;
    box-shadow: 2px 2px 2px #00000030;
    width: 260px;
    display: grid;
}
.beer-card:hover {
    background-color: #f6f5ef;
    box-shadow: 2px 2px 2px #00000062;
}
img {
    /* max-width: 250px; */
    max-height: 166px;
    object-fit: cover;
    justify-self: center;
}
.beer-buttons {
    list-style: none;
    display: flex;
    gap: .5em;
    justify-content: flex-start;
    padding-left: 0;
}
li.save-beer {
    margin-left: auto;
}
</style>