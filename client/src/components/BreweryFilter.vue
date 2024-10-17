<template>
<aside id="brewery-filters" class="filters">
    <font-awesome-icon icon="xmark" id="close" @click="$store.commit('HIDE_FILTERS')"/>
    <form >
        <input type="search" id="city-search" v-model="filters.city" placeholder="City" @keyup.prevent="updateFilters">
        <select id="state-select" v-model="filters.state" @change="updateFilters">
            <option value=""></option>
            <option v-for="state in $store.state.states" :key="state" :value="state">{{ state }}</option>
        </select>

        <fieldset id="kid-friendly-radio">
            <legend>Kid Friendly</legend>
            <div>
                <label for="kid-friendly-true">Yes </label>
                <input type="radio" name="kid-friendly" id="kid-friendly-true" :value="true" v-model="filters.kidFriendly" @change="updateFilters">
            </div>
            <div>
                <label for="kid-friendly-false">No </label>
                <input type="radio" name="kid-friendly" id="kid-friendly-false" :value="false" v-model="filters.kidFriendly" @change="updateFilters">
            </div>
            <div>
                <label for="kid-friendly-no-pref">No preference</label>
                <input type="radio" name="kid-friendly" id="kid-friendly-no-pref" :value="null" v-model="filters.kidFriendly" @change="updateFilters">
            </div>
        </fieldset>

        <fieldset id="dog-friendly-radio">
            <legend>Dog Friendly</legend>
            <div>
                <label for="dog-friendly-true">Yes </label>
                <input type="radio" name="dog-friendly" id="dog-friendly-true" :value="true" v-model="filters.dogFriendly" @change="updateFilters">
            </div>
            <div>
                <label for="dog-friendly-false">No </label>
                <input type="radio" name="dog-friendly" id="dog-friendly-false" :value="false" v-model="filters.dogFriendly" @change="updateFilters">
            </div>
            <div>
                <label for="dog-friendly-no-pref">No preference</label>
                <input type="radio" name="dog-friendly" id="dog-friendly-no-pref" :value="null" v-model="filters.dogFriendly" @change="updateFilters">
            </div>
        </fieldset>
        
        <fieldset id="serves-food-radio">
            <legend>Serves Food</legend>
            <div>
                <label for="serves-food-true">Yes </label>
                <input type="radio" name="serves-food" id="serves-food-true" :value="true" v-model="filters.servesFood" @change="updateFilters">
            </div>
            <div>
                <label for="serves-food-false">No </label>
                <input type="radio" name="serves-food" id="serves-food-false" :value="false" v-model="filters.servesFood" @change="updateFilters">
            </div>
            <div>
                <label for="serves-food-no-pref">No preference</label>
                <input type="radio" name="serves-food" id="serves-food-no-pref" :value="null" v-model="filters.servesFood" @change="updateFilters">
            </div>
        </fieldset>
    </form>
</aside>
</template>

<script>
export default {
    data() {
        return {
            filters: {
                city: '',
                state: '',
                kidFriendly: null,
                dogFriendly: null,
                servesFood: null
            },
            states: [ 'AL', 'AK', 'AS', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE', 'DC', 'FM', 'FL', 'GA', 'GU', 'HI', 'ID', 'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MH', 'MD', 'MA', 'MI', 'MN', 'MS', 'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'MP', 'OH', 'OK', 'OR', 'PW', 'PA', 'PR', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VI', 'VA', 'WA', 'WV', 'WI', 'WY' ]
        }
    },
    methods: {
        updateFilters() {
            this.$store.commit('CLEAR_BREWERY_FILTERS')
            this.$store.commit('SET_BREWERY_FILTERS', this.filters)
        }
    }
}
</script>

<style scoped>
#close {
    display: none;
}

@media only screen and (max-width: 425px) {
    #close {
        display: inline;
        align-self: flex-end;
    }
}
</style>