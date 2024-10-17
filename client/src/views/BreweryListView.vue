<template>
<div id="brewery-list" class="filtered-page">
    <BreweryFilter v-show="!$store.getters.isMobile || !$store.state.hideFilters"/>
    <div id="content">
        <h1>Breweries <font-awesome-icon icon="filter" v-if="winWid <= 425" @click="$store.commit('TOGGLE_FILTERS')"/></h1>
        <div id="cards" class="cards">
            <BreweryCard v-for="brewery in filteredBreweries" :key="brewery.breweryId" :brewery="brewery" @click="$router.push({name: 'brewery', params: {id: brewery.breweryId}})" />
        </div>
    </div>
</div>
</template>

<script>
import BreweryCard from '../components/BreweryCard.vue'
import BreweryFilter from '../components/BreweryFilter.vue';

import breweryService from '../services/BreweryService';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    components: {
        BreweryCard,
        BreweryFilter
    },
    data() {
        return {
            breweries: [],
            isLoading: true
        }
    },
    computed: {
        filteredBreweries() {
            return this.breweries.filter(brewery => this.filterBreweries(brewery))
        },
        filters() {
            return this.$store.state.breweryFilters
        },
        winWid() {
            return top.innerWidth
        },
        showFilters() {
            return this.winWid > 425 || !this.$store.state.hideFilters
        }
    },
    methods: {
        importAllBreweries() {
            breweryService.getAllBreweries()
                .then(response => {
                    this.breweries = response.data;
                })
                .catch(error => axiosErrorHandling(error))
        },
        filterBreweries(brewery) {
            if (this.filters.kidFriendly === null || this.filters.kidFriendly === brewery.kidFriendly) {
                if (this.filters.dogFriendly === null || this.filters.dogFriendly === brewery.dogFriendly) {
                    if (this.filters.servesFood === null || this.filters.servesFood === brewery.servesFood) {
                        return brewery.state.includes(this.filters.state) && brewery.city.toLowerCase().includes(this.filters.city.toLowerCase())
                    }
                }
            }
        }
    },
    created() {
        this.$store.commit('HIDE_NAV')
        this.importAllBreweries();
        this.isLoading = false;
    }
}
</script>

<style scoped>
#cards {
    display: flex;
}

aside {
    grid-area: filters;
}

#content {
    grid-area: content;
}

@media only screen and (max-width: 425px) {
    h1 {
        text-align: center;
    }
}
</style>
