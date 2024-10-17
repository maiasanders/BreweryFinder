<template>
<aside id="event-filters" class="filters">
    <font-awesome-icon icon="xmark" id="close" @click="$store.commit('HIDE_FILTERS')" v-if="$store.getters.isMobile"/>
    <form>
        <!-- <input type="search" id="city-search" v-model="filters.city" placeholder="City" @keyup.prevent="updateFilters"> -->
        <!-- <select id="state-select" v-model="filters.state" @change="updateFilters">
            <option value=""></option>
            <option v-for="state in $store.state.states" :key="state" :value="state">{{ state }}</option>
        </select> -->
        <div>
            <label for="min-date">Earliest date </label>
            <input type="date" id="min-date" name="min-date" v-model="filters.minDate" @change="updateFilters">
        </div>
        <div>
            <label for="max-date">Latest date </label>
            <input type="date" id="max-date" name="max-date" v-model="filters.maxDate" @change="updateFilters">
        </div>
        <fieldset>
            <legend>21 +</legend>
            <div>
                <input type="radio" id="over-21-true" name="over-21-true" :value="true" v-model="filters.over21" @change="updateFilters">
                <label for="over-21-true"> Yes </label>
            </div>
            <div>
                <input type="radio" id="over-21-false" name="over-21-false" :value="false" v-model="filters.over21" @change="updateFilters">
                <label for="over-21-false"> No </label>
            </div>
            <div>
                <input type="radio" id="no-pref" name="no-pref" :value="null" v-model="filters.over21" @change="updateFilters">
                <label for="no-pref"> No preference </label>
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
                // city: '',
                // state: '',
                minDate: null,
                maxDate: null,
                over21: null
            }
        }
    },
    methods: {
        updateFilters() {
            this.$store.commit('CLEAR_EVENT_FILTERS');
            this.$store.commit('SET_EVENT_FILTERS', this.filters)
        }
    }
}
</script>

<style scoped>
#close {
    display: none;
}

@media screen {
    #close {
        display: inline;
        text-align: end;
    }
}
</style>