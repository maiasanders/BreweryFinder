<template>
<div id="beer-form">
    <h1>{{ beerId > 0 ? beer.name : 'Add new beer' }}</h1>
    <form>
        <div>
            <label for="beer-name">Beer name </label>
            <input type="text" id="beer-name" name="beer-name" v-model="beer.name" />
        </div>
        <div>
            <label for="brewery-select">Brewery </label>
            <select name="brewery" id="brewery-select" v-model="beer.breweryId" placeholder="-- Please Select a Brewery --">
                <!-- <option value=""></option> -->
                <option v-for="brewery in breweries" :key="brewery.breweryId" :value="brewery.breweryId">{{ brewery.breweryName }}</option>
            </select>
        </div>
        <div>
            <label for="style-select">Style </label>
            <select name="style" id="style-select" v-model="beer.style">
                <option v-for="style in styles" :key="style.styleId" :value="style.styleId">{{ style.styleName }}</option>
            </select>
        </div>
        <div>
            <label for="abv-select">ABV </label>
            <input name="abv" id="abv-select" type="number" min="0.0" v-model="beer.abv" placeholder="0.0 %" />
        </div>
        <div>
            
        </div>
        <div class="textarea-sec">
            <label for="desc-text">Description </label>
            <textarea name="desc" id="desc-text" v-model="beer.desc" >Enter a description</textarea>
        </div>
        <div>
            <input type="checkbox" id="seasonal-check" name="seasonal" v-model="beer.seasonal">
            <label for="seasonal-check"> Seasonal</label>
        </div>
        <div>
            <label for="season-name">Season </label>
            <input type="text" id="season-name" name="season" :disabled="!beer.seasonal" v-model="beer.seasonName">
        </div>
        </form>
        <ImageUploader />
        <input type="submit" @click.prevent="submitForm">
</div>
</template>

<script>
import BreweryService from '../services/BreweryService';
import BeerService from '../services/BeerService';
import ImageUploader from '../components/ImageUploader.vue';
import { axiosErrorHandling, formatStringForSql } from '../services/ServiceUtils';

export default {
    components: {
        ImageUploader
    },
    data() {
        return {
            beer: {
                name: "",
                breweryId: 0,
                style: '',
                desc: '',
                abv: 0,
                seasonal: false,
                seasonName: null,
                imgUrl: ''
            },
            breweries: [],
            styles: [],
            isLoading: true,
            displayMsg: ''
        }
    },
    computed: {
        beerId() {
            return Number(this.$route.params.id)
        }
    },
    methods: {
        submitForm() {
            // Prep variables for uploading to DB
            this.beer.beerName = formatStringForSql(this.beer.beerName);
            this.beer.desc = formatStringForSql(this.beer.desc);
            this.beer.seasonName = formatStringForSql(this.beer.seasonName);
            this.beer.imgUrl = this.$store.state.imgUploadUrl;


            if (this.beerId === 0) {
                BeerService.postBeer(this.beer)
                    .then(response => {
                        if (response.status === 201) {
                            this.displayMsg = `${this.beer.name} has been added!`
                            let id = response.data.beerId;
                            this.$router.push({ name: 'beer', params: { id: id } })
                        }
                    })
                    .catch(error => axiosErrorHandling(error))
            } else {
                BeerService.updateBeer(this.beerId, this.beer)
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({ name: 'beer', params: { id: this.beerId } })
                        }
                    })
            }
        }
    },
    created() {
        if (this.beerId !== 0) {
            BeerService.getBeer(this.beerId)
                .then(response => {
                    // this.beerId = response.data.id;
                    this.beer = {
                        name: response.data.beerName,
                        breweryId: response.data.breweryId,
                        style: response.data.style,
                        desc: response.data.desc,
                        abv: response.data.abv,
                        seasonal: response.data.seasonal,
                        seasonName: response.data.seasonName || null,
                        imgUrl: response.data.imgUrl
                    }
                })
                .catch(error => axiosErrorHandling(error))
        }

        BreweryService.getBreweriesByBrewer()
            .then(response => {
                this.breweries = response.data;
                this.isLoading = false
            })
            .catch(error => {
                window.alert(error.status)
            });

        BeerService.getStyles()
            .then(response => {
                this.styles = response.data;
            })
    }
}
</script>

<style scoped>
#beer-form {
    padding: var(--page-content-padding);
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas:
        "title title"
        "form form"
        "img sub";
}
#image-uploader {
    width: 80%;
    display: inline-block;
    grid-area: img;
}
input[type=submit] {
    display: inline-block;
    grid-area: sub;
    align-self: end;
}
form {
    grid-area: form;
}
h1 {
    grid-area: title;
}

@media only screen and (max-width: 425px) {
    #beer-form {
        grid-template-columns: 1fr;
        grid-template-areas: "title"
                            "form"
                            "img"
                            "sub";
    }
}
</style>