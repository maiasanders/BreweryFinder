<template>
<div id="event-list" class="filtered-page">
    <EventFilters v-show="!$store.getters.isMobile || !$store.state.hideFilters" />
    <div id="content">
        <h1>Events <font-awesome-icon icon="filter" v-if="$store.getters.isMobile" @click="$store.commit('TOGGLE_FILTERS')"/></h1>
        <div id="cards" class="cards">
            <EventCard 
                v-for="event in filteredEvents" 
                :key="event.id" 
                :event="event" 
                @click="$router.push({name: 'eventDetails', params: {id: event.id}})"/>
        </div>
    </div>
</div>
</template>

<script>
import EventService from '../services/EventService'

import EventFilters from '../components/EventFilters.vue'
import EventCard from '../components/EventCard.vue'

export default {
    components: {
        EventFilters,
        EventCard
    },
    data() {
        return {
            events: []
        }
    },
    computed: {
        filteredEvents() {
            return this.events.filter(event => this.filterEvent(event))
        },
        minDate() {
            return new Date(this.$store.state.eventFilter.minDate)
        },
         maxDate() {
            return new Date(this.$store.state.eventFilter.minDate) || null
         },
         showFilters() {
            return this.winWid > 425 || !this.$store.state.hideFilters
        },
        winWid() {
            return top.innerWidth;
        }
    },
    methods: {
        filterEvent(event) {
            let date = new Date(event.eventDate).valueOf();
            if (this.$store.state.eventFilter.over21 === null || this.$store.state.eventFilter.over21 === event.is21Up) {
                if (!this.$store.state.eventFilter.minDate || this.minDate.valueOf() <= date) {
                    return !this.$store.state.eventFilter.maxDate || this.maxDate.valueOf() <= date;
                }
            }
        }
    },
    created() {
        this.$store.commit('HIDE_NAV')
        EventService.getAllEvents()
            .then(response => {
                this.events = response.data;
            })
    },
    unmounted() {
        this.$store.commit('CLEAR_EVENT_FILTERS')
    }
}
</script>

