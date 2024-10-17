<template>
<div id="brewery-details" class="filtered-page">
    <BeerFilters v-show="showFilters"/>
    <div class="page-content" >
        <h4 v-if="displayMessage">{{ displayMessage }}</h4>
        <BreweryInfo :brewery="brewery" />
        <div id="beer-cards" class="cards">
            <BeerCard 
                v-for="beer in filteredBeers" 
                :key="beer.beerId" 
                :beer="beer" 
                :showBrewery="true" />
        </div>
    </div>
</div>
</template>

<script>
import BeerService from '../services/BeerService'
import BreweryService from '../services/BreweryService';

import BeerCard from '../components/BeerCard.vue';
import BreweryInfo from '../components/BreweryInfo.vue';
import BeerFilters from '../components/BeerFilters.vue';

export default {
    components: {
        BeerCard,
        BreweryInfo,
        BeerFilters
    },
    data() {
        return {
            brewery: {},
            beers: [],
            isLoading: true,
            displayMessage: null
        }
    },
    computed: {
        filteredBeers() {
            return this.beers.filter((beer) => {
                if (this.filters.minAbv === null || beer.abv >= this.filters.minAbv) {
                    if (this.filters.maxAbv === null || beer.abv <= this.filters.maxAbv || this.filters.maxAbv === 0) {
                        return this.checkForQuery(beer)
                    }
                }
            })
        },
        filters() {
            return this.$store.state.beerFilters;
        },
        winWid() {
            return top.innerWidth;
        },
        showFilters() {
            return this.winWid > 425 || !this.$store.state.hideFilters
        }
    },
    methods: {
        getBrewery(){
            BreweryService.getBrewery(this.$route.params.id)
                .then(response => {
                    this.brewery = response.data
                })
                .catch(error => this.errorHandling(error))
        },
        getBeers() {
            BeerService.getByBrewery(this.$route.params.id)
                .then(response => {
                    this.beers = response.data
                    this.isLoading = false
                })
                .catch(error => this.errorHandling(error))
        },
        errorHandling(error) {
            if (error.response) {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login' })
                } else {
                    this.displayMessage = "Oops! We're having some problems, try again later."
                }
            } else if (error.request) {
                this.displayMessage = "Looks like our system is having trouble connecting, try again later."
            }
        },
        checkForQuery(beer) {
            return beer.beerName.toLowerCase().includes(this.filters.query.toLowerCase()) 
                || beer.brewery_name.toLowerCase().includes(this.filters.query.toLowerCase())
                || beer.styleName.toLowerCase().includes(this.filters.query.toLowerCase())
                || beer.desc.toLowerCase().includes(this.filters.query.toLowerCase())
                || beer.seasonal && (beer.seasonName.toLowerCase().includes(this.filters.query.toLowerCase()))
        },
        updateForResize() {
            if (top.innerWidth > 425) {
                this.$store.commit('SHOW_FILTERS')
                this.$store.commit('HIDE_NAV')
                return true;
            } else {
                this.$store.commit('HIDE_FILTERS')
                this.$store.commit('HIDE_NAV')
            }
        }
    },
    created() {
        this.getBrewery();
        this.getBeers();
    }
}
</script>

<style scoped>
/* #brewery-details {
    display: grid;
    grid-template-columns: 200px 1fr;
    grid-template-areas: "filters info"
                        "filter cards";
} */
.content {
    grid-area: content;
}

aside {
    grid-area: filters;
}

#brewery-info {
    grid-area: info;
}

#beer-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 50px;
    justify-content: space-evenly;
    grid-area: cards;
}

@media only screen and (max-width: 425px) {
    #brewery-details {
        grid-template-columns: 1fr;
    grid-template-areas:
        "info"
        "cards";
    }
}
</style>