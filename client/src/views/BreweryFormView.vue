<template>
<div id="brewery-form">
    <h1>{{ breweryId > 0 ? brewery.breweryName : 'New Brewery' }}</h1>
    <form @submit.prevent="submitBrewery">
        <div>
            <label for="brewery-name-in">Brewery name </label>
            <input type="text" id="brewery-name-in" name="brewery-name" v-model="brewery.breweryName">
        </div>
        <div>
            <label for="address-in">Address </label>
            <input type="text" id="address-in" name="address" v-model="brewery.address">
        </div>
        <div>
            <label for="city-in">City </label>
            <input type="text" id="city-in" name="city" v-model="brewery.city">
        </div>
        <div>
            <label for="state-select">State </label>
            <select id="state-select" name="state" v-model="brewery.state">
                <option v-for="state in $store.state.states" :key="state" :value="state">{{ state }}</option>
            </select>
        </div>
        <div>
            <label for="zip-in">Zip </label>
            <input type="text" id="zip-in" name="zip" v-model="brewery.zip">
        </div>
        <label for="about-us-in">About Us </label>
        <textarea id="about-us-in" name="about-us" v-model="brewery.aboutUs" placeholder="Please enter a short description of your brewery's philosophy, facilities, etc."></textarea>
        <!-- days open checks -->
        <fieldset>
            <legend>Open </legend>
            <div>
                <input type="checkbox" id="su" name="days-open" value="Su" v-model="days">
                <label for="su"> Sunday</label>
            </div>
            <div>
                <input type="checkbox" id="m" name="days-open" value="M" v-model="days">
                <label for="m"> Monday</label>
            </div>
            <div>
                <input type="checkbox" id="tu" name="days-open" value="Tu" v-model="days">
                <label for="tu"> Tuesday</label>
            </div>
            <div>
                <input type="checkbox" id="w" name="days-open" value="W" v-model="days">
                <label for="w"> Wednesday</label>
            </div>
            <div>
                <input type="checkbox" id="th" name="days-open" value="Th" v-model="days">
                <label for="th"> Thursday</label>
            </div>
            <div>
                <input type="checkbox" id="f" name="days-open" value="F" v-model="days">
                <label for="f"> Friday</label>
            </div>
            <div>
                <input type="checkbox" id="sa" name="days-open" value="Sa" v-model="days">
                <label for="sa">Saturday</label>
            </div>
            <div>
                <label for="open-time">Open time </label>
                <input type="time" id="open-time" :initial="brewery.openTime" v-model="brewery.openTime">
            </div>
            <div>
                <label for="close-time">Close time </label>
                <input type="time" id="close-time" v-model="brewery.closeTime">
            </div>
        </fieldset>
        <div>
            <label for="website-in">Website </label>
            <input type="text" id="website-in" name="website" v-model="brewery.website">
        </div>
        <div>
            <input type="checkbox" id="serves-food-check" name="serves-food" v-model="brewery.servesFood">
            <label for="serves-food-check"> Serves Food</label>
        </div>
        <div>
            <input type="checkbox" id="food-trucks-check" name="food-truck" v-model="brewery.hasFoodTrucks">
            <label for="food-trucks-check"> Food Trucks</label>
        </div>
        <div>
            <input type="checkbox" id="kid-friendly-check" name="kid-friendly" v-model="brewery.isKidFriendly">
            <label for="kid-friendly-check"> Kid Friendly</label>
        </div>
        <div>
            <input type="checkbox" id="dog-friendly-check" name="dog-friendly" v-model="brewery.isDogFriendly">
            <label for="dog-friendly-check"> Dog Friendly</label>
        </div>
    </form>
    <ImageUploader />
    <input type="submit" @click.prevent="submitBrewery">
</div>
</template>

<script>
import BreweryService from '../services/BreweryService'
import { axiosErrorHandling, formatStringForSql } from '../services/ServiceUtils';

import ImageUploader from '../components/ImageUploader.vue';

export default {
    components: {
        ImageUploader
    },
    data() {
        return {
            breweryId: 0,
            brewery: {
                breweryName: '',
                brewerId: '',
                address: '',
                city: '',
                state: '',
                zip: '',
                aboutUs: '',
                servesFood: false,
                hasFoodTrucks: false,
                isKidFriendly: false,
                isDogFriendly: false,
                daysOpen: '',
                openTime: '',
                closeTime: '',
                website: ''
            },
            days: []
        }
    },
    methods: {
        updateImg() {
            this.brewery.picUrl = this.$store.state.imgUploadUrl;
        },
        submitBrewery() {
            // Prep variables for SQL formatting
            this.brewery.breweryName = formatStringForSql(this.brewery.breweryName);
            this.brewery.address = formatStringForSql(this.brewery.address);
            this.brewery.city = formatStringForSql(this.brewery.city);
            this.brewery.aboutUs = formatStringForSql(this.brewery.aboutUs);
            this.brewery.picUrl = this.$store.state.imgUploadUrl;
            this.brewery.daysOpen = this.days.join(',');
            this.brewery.openTime = this.convertInputToString(this.brewery.openTime);
            this.brewery.closeTime = this.convertInputToString(this.brewery.closeTime)

            if (this.breweryId === 0) {
                BreweryService.createBrewery(this.brewery)
                    .then(response => {
                        if (response.status === 201) {
                            this.$store.commit('CLEAR_IMG_URL')
                            this.$router.push({ name: 'brewery', params: { id: response.data.breweryId } })
                        }
                    })
                    .catch(error => axiosErrorHandling(error))
            } else {
                BreweryService.updateBrewery(this.breweryId, this.brewery)
                    .then(response => {
                        this.$router.push({ name: 'brewery', params: { id: this.breweryId } })
                    })
                    .catch(error => axiosErrorHandling(error))
            }
        },
        convertTimeToInputFormat(time) {
            if (time.includes('AM')) {
                return time.slice(0, 5)
            } else {
                let hrs = Number(time.slice(0, 2)) + 12;
                return `${hrs}:${time.slice(3, 5)}`
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
        this.brewery.brewerId = this.$store.state.user.id;
        // If editing existing brewery, pull in vals from API call
        if (this.$route.params.id > 0) {
            this.breweryId = this.$route.params.id;
            BreweryService.getBrewery(this.breweryId)
                .then(response => {
                    this.brewery = {
                        breweryName: response.data.breweryName,
                        brewerId: response.data.brewerId,
                        address: response.data.address,
                        city: response.data.city,
                        state: response.data.state,
                        zip: response.data.zip,
                        aboutUs: response.data.aboutUs,
                        servesFood: response.data.servesFood,
                        hasFoodTrucks: response.data.hasFoodTrucks,
                        isKidFriendly: response.data.kidFriendly,
                        isDogFriendly: response.data.dogFriendly,
                        daysOpen: response.data.daysOpen,
                        openTime: this.convertTimeToInputFormat(response.data.openTime),
                        closeTime: this.convertTimeToInputFormat(response.data.closeTime),
                        website: response.data.website,
                        imgUrl: response.data.imgUrl
                    };
                    this.days = response.data.daysOpen.split(',')
                })
        }
    }
}
</script>