<template>
<form>
    <div>
        <label for="name-in">Event name </label>
        <input type="text" id="name-in" name="name-in" v-model="newEvent.eventName">
    </div>
    <select id="brewery" v-model="newEvent.breweryId" placeholder="Brewery">
            <option v-for="brewery in breweries" :key="brewery.breweryId" :value="brewery.breweryId">{{ brewery.breweryName }}</option>
        </select>
    <div>
        <label for="event-date">Date</label>
        <input type="date" v-model="newEvent.eventDate" id="event-date" name="event-date">
    </div>
    <div>
        <label for="start-time">Start </label>
        <input type="time" id="start-time" name="start-time">
    </div>
    <div>
        <label for="end-time">End </label>
        <input type="time" id="end-time" name="end-time">
    </div>
    <div>
        <input type="checkbox" id="over-21" name="over-21" v-model="newEvent.over21">
        <label for="over-21"> 21 +</label>
    </div>
    <div>
        <label for="desc">Description</label>
        <textarea id="desc" name="desc" v-model="newEvent.desc">Please add a description...</textarea>
    </div>
    <fieldset>
        <legend>Categories (Select all that apply)</legend>
        <div v-for="category in categoryOptions" :key="category.id">
            <input type="checkbox" :id="category.categoryName" :name="category.categoryName" :value="category" v-model="newEvent.categories">
            <label :for="category.categoryName">{{ category.categoryName }}</label>
        </div>
    </fieldset>
    <div id="selected-categories">
        <div v-for="category in newEvent.categories" :key="category.id">{{ category.categoryName }}</div>
    </div>
    <input type="submit" @click.prevent="submitEvent">
</form>
</template>

<script>
import EventService from '../services/EventService';
import BreweryService from '../services/BreweryService';
import CategoryService from '../services/CategoryService'
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    props: {
        event: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            id: this.event.id,
            newEvent: {
                eventName: this.event.eventName,
                breweryId: this.event.breweryId,
                eventDate: this.event.eventDate,
                begins: this.event.begins,
                end: this.event.ends,
                desc: this.event.desc,
                over21: this.event.is21Up,
                categories: this.event.categories
            },
            breweries: [],
            categoryOptions: [],
            selectedCategories: this.event.categories
        }
    },
    methods: {
        submitEvent() {
            if (this.id !== 0) {
                EventService.updateEvent(this.id, this.newEvent)
            } else {
                EventService.postEvent(this.newEvent)
            }
        },
        convertTimeToInputFormat(time) {
            if (time.includes('AM')) {
                return time.slice(0, 5)
            } else {
                let hrs = Number(time.slice(0, 2)) + 12;
                return hrs + time.slice(3, 5)
            }
        },
        convertInputToString(time) {
            let hrs = Number(time.slice(0, 2));
            let mins = Number(time.slice(3, 5));
            let amPm = ''
            if (hrs === 0) {
                hrs = 12;
                amPm = "AM";
            } else if (hrs < 12) {
                amPm = 'PM'
            } else if (hrs >= 12) {
                amPm = 'PM'
                if (hrs > 12) hrs -= 12
            }
            return `${hrs < 10 ? '0' + hrs : hrs}:${mins < 10 ? '0' + mins : mins} ${amPm}`
        }
    },
    created() {

        BreweryService.getBreweriesByBrewer(this.$store.state.user.id)
            .then(response => {
                this.breweries = response.data
            })
            .catch(error => axiosErrorHandling(error))
        CategoryService.getCategories()
            .then(response => {
                this.categoryOptions = response.data
            })
            .catch(error => axiosErrorHandling(error))

        this.$store.dispatch('getCategories')
            .then(result => this.categoryOptions = result.data)
    }
}
</script>

<style scoped>

</style>