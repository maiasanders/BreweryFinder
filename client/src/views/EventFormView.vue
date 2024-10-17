<template>
<div id="event-form-view">
    <h1>{{ $route.params.id !== '0' ? event.eventName : 'New Event' }}</h1>
    <!-- <EventForm :event="event" /> -->
    <form>
        <div>
            <label for="name-in">Event name </label>
            <input type="text" id="name-in" name="name-in" v-model="event.eventName">
        </div>
        <select id="brewery" v-model="event.breweryId" placeholder="Brewery">
            <option v-for="brewery in breweries" :key="brewery.breweryId" :value="brewery.breweryId">{{ brewery.breweryName }}</option>
        </select>
        <div>
            <label for="event-date">Date</label>
            <input type="date" v-model="event.eventDate" id="event-date" name="event-date">
        </div>
        <div>
            <label for="start-time">Start </label>
            <input type="time" id="start-time" name="start-time" v-model="event.begins">
        </div>
        <div>
            <label for="end-time">End </label>
            <input type="time" id="end-time" name="end-time" v-model="event.ends">
        </div>
        <div>
            <input type="checkbox" id="over-21" name="over-21" v-model="event.over21">
            <label for="over-21"> 21 +</label>
        </div>
        <div class="textarea-sec">
            <label for="desc">Description</label>
            <textarea id="desc" name="desc" v-model="event.desc">Please add a description...</textarea>
        </div>
        <!-- <div>
        <label for="category-select">Categories (All that apply)</label>
        <select id="category-select" name="category-select" v-model="event.categories" multiple="true">
                <option 
                    v-for="category in categoryOptions" 
                    :key="category.id" 
                    :value="category" >{{ category.categoryName }}</option>
            </select>
    </div> -->
        <!-- <fieldset>
            <legend>Categories (Select all that apply)</legend>
            <div v-for="category in categoryOptions" :key="category.id">
                <input type="checkbox" :id="category.categoryName" :name="category.categoryName" :value="category" v-model="event.categories">
                <label :for="category.categoryName">{{ category.categoryName }}</label>
            </div>
        </fieldset>
        <div id="selected-categories">
            <div v-for="category in event.categories" :key="category.id">{{ category.categoryName }}</div>
        </div> -->
    </form>
    <EventCategorySelector 
        :category-options="$store.getters.getCategories" 
        :selected-categories="event.categories" />
    <ImageUploader />
    <input type="submit" @click.prevent="submitEvent">
</div>
</template>

<script>
import EventForm from '../components/EventForm.vue';
import EventCategorySelector from '../components/EventCategorySelector.vue';
import ImageUploader from '../components/ImageUploader.vue';

import { convertTimeToInputFormat, convertInputToString, axiosErrorHandling, formatStringForSql } from '../services/ServiceUtils'
import EventService from '../services/EventService';
import BreweryService from '../services/BreweryService';
import CategoryService from '../services/CategoryService';


export default {
    components: { EventForm, EventCategorySelector, ImageUploader },
    data() {
        return {
            id: this.$route.params.id,
            event: {
                eventName: '',
                breweryId: null,
                eventDate: null,
                begins: null,
                ends: null,
                desc: '',
                over21: false,
                categories: [],
                imgUrl: ''
            },
            breweries: [],
            categoryOptions: [],
        }
    },
    computed: {
        selectedCategories() {
            return this.$store.getters.getSelectedCategories
        }
    },
    methods: {
        submitEvent() {
            // format attributes for API/SQL
            this.event.eventName = formatStringForSql(this.event.eventName);
            this.event.desc = formatStringForSql(this.event.desc)
            this.event.categories = this.selectedCategories.map(cat => cat.categoryName);
            this.event.begins = convertInputToString(this.event.begins)
            this.event.ends = convertInputToString(this.event.ends)
            this.event.imgUrl = this.$store.state.imgUploadUrl;

            if (this.id !== '0') {
                EventService.updateEvent(this.$route.params.id, this.event)
                    .then(response => {
                        this.$router.push({ name: 'eventDetails', params: { id: this.id } })
                    })
                    .catch(error => axiosErrorHandling(error))
            } else {
                EventService.postEvent(this.event)
                    .then(response => {
                        this.$router.push({ name: 'eventDetails', params: { id: response.data.id } })
                    })
                    .catch(error => {
                        window.alert('error posting event')
                    })
            }
        }
        // convertTimeToInputFormat(time) {
        //     if (time.includes('AM')) {
        //         return time.slice(0, 5)
        //     } else {
        //         let hrs = Number(time.slice(0, 2)) + 12;
        //         return `${hrs}:${time.slice(3, 5)}`
        //     }
        // },
        // convertInputToString(time) {
        //     let hrs = Number(time.slice(0, 2));
        //     let mins = Number(time.slice(3, 5));
        //     let amPm = ''
        //     if (hrs === 0) {
        //         hrs = 12;
        //         amPm = "AM";
        //     } else if (hrs < 12) {
        //         amPm = 'PM'
        //     } else if (hrs >= 12) {
        //         amPm = 'PM'
        //         if (hrs > 12) hrs -= 12
        //     }
        //     return `${hrs < 10 ? '0' + hrs : hrs}:${mins < 10 ? '0' + mins : mins} ${amPm}`
        // }
    },
    created() {
        this.$store.dispatch('fetchCategories')
            .then(response => {
                // if (response.status === 200) {
                this.categoryOptions = response.data
                // }
            })
            .catch(error => axiosErrorHandling(error))

        if (this.$route.params !== 0) {
            EventService.getEventById(this.$route.params.id)
                .then(response => {
                    this.event = {
                        eventName: response.data.eventName,
                        breweryId: response.data.breweryId,
                        eventDate: response.data.eventDate,
                        begins: this.convertTimeToInputFormat(response.data.begins),
                        ends: this.convertTimeToInputFormat(response.data.ends),
                        desc: response.data.desc,
                        over21: response.data.is21Up,
                        categories: response.data.categories
                    }
                })
        }
        // this.event.begins = this.convertTimeToInputFormat(this.event.begins);
        // this.event.ends = this.convertTimeToInputFormat(this.event.ends)
        BreweryService.getBreweriesByBrewer(this.$store.state.user.id)
            .then(response => {
                this.breweries = response.data
            })
            .catch(error => axiosErrorHandling(error))
        // CategoryService.getCategories()
        //     .then(response => {
        //         this.categoryOptions = response.data
        //     })
        //     .catch(error => axiosErrorHandling(error))
    }
}
</script>

<style scoped>
#event-form-view {
    padding: var(--page-content-padding);
    display: grid;
    grid-template-columns: 2fr 1fr 100px;
    grid-template-areas: "title title title"
                        "form form form"
                        "cats img sub";
}

h1 {
    grid-area: title;
}
form {
    grid-area: form;
}
#cat-selector {
    grid-area: cats;
}
#image-uploader {
    grid-area: img;
}
input[type=submit] {
    grid-area: sub;
}

@media only screen and (max-width: 425px) {
    #event-form-view{
        grid-template-columns: 1fr;
        grid-template-areas: "title" "form" "img" "cats" "sub";
    }
}
</style>