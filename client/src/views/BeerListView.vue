<template>
    <div id="beer-list-view" class="filtered-page">
        <BeerFilters v-show="!$store.getters.isMobile || !$store.state.hideFilters"/>
        <div id="content" class="page-content">
            <h1>
                Beers     
                <font-awesome-icon icon="filter" id="filter-btn" @click="$store.commit('TOGGLE_FILTERS')"/>
            </h1>

            <div class="cards">
                <BeerCard v-for="beer in filteredBeers" :key="beer.beerId" :beer="beer" :showBrewery="true"/>
            </div>
        </div>
    </div>
</template>

<script>
import { storeKey } from 'vuex';
import BeerCard from '../components/BeerCard.vue';
import BeerFilters from '../components/BeerFilters.vue';

import BeerService from '../services/BeerService';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    components: {
        BeerCard,
        BeerFilters
    },
    data() {
        return {
            beers: [],
            isLoading: true
        }
    },
    computed: {
        filteredBeers() {
            return this.beers.filter((beer) => {
                if (this.filters.minAbv === null || beer.abv >= this.filters.minAbv) {
                    if (this.filters.maxAbv === null || beer.abv <= this.filters.maxAbv) {
                        return this.checkForQuery(beer)
                    }
                }
            })
        },
        filters() {
            return this.$store.state.beerFilters;
        },
        showFilters() {
            return top.innerWidth > 425 || !this.$store.state.hideFilters
        }
    },
    methods: {
        checkForQuery(beer) {
            return beer.beerName.toLowerCase().includes(this.filters.query.toLowerCase()) 
                || beer.brewery_name.toLowerCase().includes(this.filters.query.toLowerCase())
                || beer.styleName.toLowerCase().includes(this.filters.query.toLowerCase())
                || beer.desc.toLowerCase().includes(this.filters.query.toLowerCase())
                || beer.seasonal && (beer.seasonName.toLowerCase().includes(this.filters.query.toLowerCase()))
        }
    },
    created() {
        this.$store.commit('HIDE_NAV')
        BeerService.getAllBeers()
            .then(response => {
                this.beers = response.data
                this.isLoading = false
            })
            .catch(error => axiosErrorHandling(error))
    }
}
</script>

<style scoped>
/* #beer-list-view {
    display: grid;
    grid-template-columns: 200px 1fr;
    grid-template-areas: "filters list";
} */

#beer-filters {
    grid-area: filters;
}
#content {
    grid-area: content;
}
#filter-btn {
    display: none;
}

@media only screen and (max-width: 425px) {
    #filter-btn {
        display: inline;
    }
}
</style>