<template>
<div id="brewer-mode-view" class="page-content">
    <EditBreweryMenu v-for="brewery in breweries" :key="brewery.breweryId" :brewery="brewery" />
</div>
</template>

<script>
import EditBreweryMenu from '../components/EditBreweryMenu.vue';

import BreweryService from '../services/BreweryService';

export default {
    components: {
        EditBreweryMenu
    },
    data() {
        return {
            breweries: []
        }
    },
    created() {
        BreweryService.getBreweriesByBrewer(this.$store.state.user.id)
            .then(response => {
                this.breweries = response.data
            })
    }
}
</script>