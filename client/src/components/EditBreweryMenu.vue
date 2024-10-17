<template>
<div id="edit-brewery-menu">
    <div class="edit-brewery">
            <h3>{{ brewery.breweryName }}</h3>
            <font-awesome-icon icon="pen-to-square" @click="$router.push({name: 'breweryForm', params: {id: brewery.breweryId}})" />
            <!-- <font-awesome-icon icon="xmark" /> -->
        <!-- TODO add routing method to delete brewery -->
    </div>
    <h4>Beers</h4>
    <div class="beers">
        <div class="edit-beer" v-for="beer in beers" :key="beer.beerId" >
            <p>
                {{ beer.beerName }}
            </p>
            <font-awesome-icon icon="pen-to-square" @click="goToBeerForm(beer.beerId)" />
            <font-awesome-icon icon="xmark" @click="deleteBeer(beer.beerId)" />
        </div>
        <p class="add-beer" @click="$router.push({name: 'beerForm', params: {id: 0}})">
            <font-awesome-icon icon="plus" /> Add new beer
        </p>
    </div>
    <h4>Events</h4>
    <div class="events">
        <div class="edit-event" v-for="event in events" :key="event.id">
            <p >
                {{ event.eventName }} ({{ event.eventDate }})
            </p>
            <font-awesome-icon icon="pen-to-square" @click="gotToEventForm(event.id)" />
            <font-awesome-icon icon="xmark" @click="onDeleteEvent(event)" />
        </div>
        <p class="add-event" @click="$router.push({name: 'eventForm', params: {id: 0}})">
            <font-awesome-icon icon="plus" /> Add new event
        </p>
    </div>
</div>
</template>

<script>
import BeerService from '../services/BeerService';
import EventService from '../services/EventService';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    props: {
        brewery: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            beers: [],
            events: [],
            dialogInput: '',
            idToDelete: 0,
            deleteBeerRef: null,
            deleteEventRef: null
        }
    },
    methods: {
        deleteBeer(id) {
            // TODO add dialog to confirm
            BeerService.deleteBeer(id)
                .then(response => {
                    if (response.status === 204) {
                        for (let i = 0; i < this.beers.length; i++) {
                            this.beers.splice(i, 1)
                        }
                    }
                })
                .catch(error => axiosErrorHandling(error))
        },
        goToBeerForm(id) {
            this.$router.push({ name: 'beerForm', params: { id: id } })
        },
        gotToEventForm(id) {
            this.$router.push({ name: 'eventForm', params: {id: id}})
        },
        deleteEvent(id) {
            EventService.deleteEvent(id)
                .then(response => {
                    if (response.status === 204) {
                        for (let i = 0; i < this.events.length; i++) {
                            if (this.events[i].id === id) {
                                this.events.splice(i, 1);
                            }
                        }
                    }
                })
                .catch(error => axiosErrorHandling)
        }
    },
    created() {
        BeerService.getByBrewery(this.brewery.breweryId)
            .then(response => {
                this.beers = response.data
            })
            .catch(error => axiosErrorHandling(error))
        EventService.getEventsByBrewery(this.brewery.breweryId)
            .then(response => {
                this.events = response.data
            })
            .catch(error => axiosErrorHandling(error))
    }
}
</script>

<style scoped>
.edit-brewery-menu {
    margin-bottom: 15px;
}
.edit-brewery {
    margin-bottom: 40px;
    display: flex;
    justify-content: start;
    gap: 10px;
}

.beers, .events {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: space-between;
    margin-bottom: 40px;
}
.edit-beer, .edit-event {
    display: flex;
    justify-content: flex-end;
    width: 30%;
    gap: 5px;
}
.edit-beer p, .edit-event p {
    margin-right: auto;
}
</style>