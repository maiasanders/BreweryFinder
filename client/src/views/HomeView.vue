<template>
  <div class="home">
    <!-- <div id="heading-line">
      <h1>
        Home
        
      </h1>
    </div>
    <h2>Loading spinner demonstration</h2>
    <p>
      This is a demonstration of how you can show or hide a "spinner" icon to
      let the user know something is happening. Before calling an API, you'd set
      the data property <code>isLoading</code> to <code>true</code>. When the
      call completes, set it to <code>false</code>.
    </p>
    <p>
      For this demonstration, clicking the checkbox below sets
      <code>isLoading</code> to <code>true</code>, so it simulates what the user
      would see when your app is accessing an API.
    </p>
    <input type="checkbox" name="loading" id="loading" v-model="isLoading" /> Is
    Loading
    <p id="login-message" v-if="!isLoggedIn">
      Welcome! You may browse anonymously as much as you wish,<br />
      but you must
      <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add
      items to your shopping cart.
    </p>
    <h2>Font-awesome demonstration</h2>
    <p>
      This code shows you how you can include Font-awesome icons on your page. Below are two icons:
      one to indicate a "tile" view of products, and another to indicate a "table" view. There's also a little bit
      of styling to get you started, but you can style it your own way. And there's a property to track which view is "active".
    </p>
    <font-awesome-icon
      v-bind:class="{ 'view-icon': true, active: cardView }"
      v-on:click="cardView = true"
      icon="fa-solid fa-grip"
      title="View tiles"
    />
    <font-awesome-icon
      v-bind:class="{ 'view-icon': true, active: !cardView }"
      v-on:click="cardView = false"
      icon="fa-solid fa-table"
      title="View table"
    /> -->
    <loading-spinner id="spinner" v-bind:spin="isLoading" />
    <h2>Welcome!</h2>
    <home-page-hero 
        :brewery="featuredBrewery" 
        @click="$router.push({name: 'brewery', params: {id: featuredBrewery.breweryId}})"/>
    <div id="events">
        <featured-event-card 
            v-for="event in featuredEvents" 
            :key="event.eventId" 
            :event="event" 
            @click="$router.push({name: 'eventDetails', params: {id: event.id}})"/>
    </div>
  </div>
</template>

<script>
// component imports
import LoadingSpinner from "../components/LoadingSpinner.vue";
import HomePageHero from "../components/HomePageHero.vue";
import FeaturedEventCard from "../components/FeaturedEventCard.vue";


// Service imports
import breweryService from "../services/BreweryService";
import EventService from "../services/EventService"
import { axiosErrorHandling } from "../services/ServiceUtils";

export default {
  components: {
    LoadingSpinner,
    HomePageHero,
    FeaturedEventCard
  },
  data() {
    return {
      isLoading: true,
      cardView: true,
      featuredBrewery: {},
      featuredEvents: []
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },
  created() {
    this.$store.commit('HIDE_NAV')
    let ind = Math.ceil(Math.random() * 2);
    breweryService.getBrewery(ind)
        .then(response => {
            this.featuredBrewery = response.data
            this.isLoading = false
        })
        .catch(error => axiosErrorHandling(error));
    EventService.getFeaturedEvents()
        .then(response => {
            this.featuredEvents = response.data
            this.isLoading = false
        })
        .catch(error => axiosErrorHandling(error))
  }
};
</script>

<style scoped>
#spinner {
  color: green;
  position: absolute;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: lightgreen;
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color: blue;
  background-color: rgba(255, 255, 255, 0.7);
}

/* My styles */
.home {
    display: flex;  
    gap: 20px;
    flex-direction: column;
    align-items: center;
}
#hero {
    width: 100%;
}
#events {
    display: flex;
    gap: 20px;
    width: 98%;
}
#events > div {
    flex: 1 1 min-content;
}

/* Mobile styling */
@media only screen and (max-width: 425px) {
    .home {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 30px 4fr 1fr;
        grid-template-areas: "welcome" "hero" "events";
        padding: 10px;
    }
    h2 {
        grid-area: welcome;
    }
    #hero {
        grid-area: hero;
    }
    #events {
        grid-area: events;
        position: absolute;
        top: 75%;
        bottom: 20px;
        height: auto;
        width: 95%;
    }
    .featured-event:nth-child(3), .featured-event:last-child {
        display: none;
    }
}
</style>