<template>
    <aside id="beer-filters" class="filters">
        <font-awesome-icon icon="xmark" id="close" @click="$store.commit('HIDE_FILTERS')"/>
        <input type="search" placeholder="Search..." v-model="filters.query" @keyup.prevent="updateFilters" />
        <div id="abv-control">
            <label for="min-abv-input">ABV </label>
            <input type="number" id="min-abv-input" name="min-abv" v-model="filters.minAbv" @keyup.prevent="updateFilters" />
            <label for="max-abv-input"> - </label>
            <input type="number" id="max-abv-input" name="max-abv" v-model="filters.maxAbv" default="100" @keyup.prevent="updateFilters" />
        </div>
    </aside>
</template>

<script>
export default {
    data() {
        return {
            filters: {
                query: '',
                minAbv: null,
                maxAbv: null,
            }
        }
    },
    computed: {
        winWid: top.innerWidth
    },
    methods: {
        updateFilters() {
            this.$store.commit('CLEAR_BEER_FILTERS')
            this.$store.commit('UPDATE_BEER_FILTERS', this.filters)
        }
    }
}
</script>

<style scoped>
/* #beer-filters {
    display: flex;
    flex-direction: column;  
} */

#close {
    display: none;
}

#abv-control {
    display: flex;
    justify-content: space-between;
    max-width: 100%;
}
#abv-control input {
    flex-shrink: 1;
    flex-basis: content;
    max-width: 30%
}

@media only screen and (max-width: 425px) {
    #close {
        display: inline;
        align-self: flex-end;
    }
}
</style>