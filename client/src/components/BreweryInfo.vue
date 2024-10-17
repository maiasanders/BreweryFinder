<template>
<div id="brewery-info">
    <h2 id="brewery-name">{{ brewery.breweryName }}</h2>
    <div id="external-links">
        <a :href="brewery.website" id="official-site">Official Site</a>
        <!-- TODO: add in social links if/when added to backend -->
        <!-- <a href="#" id="facebook"><i class="fa-brands fa-facebook"></i></a>
        <a href="#" id="x"><i class="fa-brands fa-square-x-twitter"></i></a>
        <a href="#" id="insta"><i class="fa-brands fa-square-instagram"></i></a> -->
    </div>
    <section id="brewery-address">
        <p>{{ brewery.address }}</p>
        <p>{{ brewery.city }}, {{ brewery.state }} {{ brewery.zip }}</p>
    </section>
    <section id="about-us">
        <p>{{ brewery.aboutUs }}</p>
    </section>
    <section id="brewery-details">
        <ul>
            <li id="serves-food">Serves food {{ checkOrX(brewery.servesFood) }}</li>
            <li id="food-truck">Food trucks {{ checkOrX(brewery.hasFoodTrucks) }}</li>
            <li id="kids">Kid friendly {{ checkOrX(brewery.isKidFriendly) }}</li>
            <li id="dogs">Dog friendly {{ checkOrX(brewery.isDogFriendly) }}</li>
            <li id="open">Open: {{ brewery.daysOpen.split(',').join(' ') }} {{ brewery.openTime }} - {{ brewery.closeTime }}</li>
        </ul>
        <font-awesome-icon icon="filter" v-if="winWid <= 425" @click="$store.commit('TOGGLE_FILTERS')"/>
    </section>
    
</div>
</template>

<script>
export default {
    props: {
        brewery: {
            type: Object,
            required: true
        }
    },
    computed: {
        winWid() {
            return top.innerWidth;
        }
    },
    methods: {
        checkOrX(bool) {
            return bool ? '✅' : '❌';
        }
    }
}
</script>

<style scoped>
#brewery-info {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "name address" "social details" "about details";
}

#brewery-name {
    grid-area: name;
    font-family: "Antonio", sans-serif;
}

#external-links {
    grid-area: social;
}

#brewery-address {
    grid-area: address;
    display: flex;
    flex-direction: column;
    text-align: end;
}

#brewery-address>p {
    margin: 0;
}

#about-us {
    grid-area: about;
}

#brewery-details {
    grid-area: details;
    text-align: end;
}

#brewery-details ul {
    list-style: none;
    padding-left: 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

#brewery-details li {
    padding-left: 0;
}

@media only screen and (max-width: 425px) {
    #brewery-info {
        grid-template-columns: 1fr;
        grid-template-areas: "name" "social" "about" "address" "details";
    }
    #brewery-address {
        flex-direction: row;
        justify-content: space-between;
    }
    #brewery-details ul {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        justify-content: space-between;
    }
}
</style>