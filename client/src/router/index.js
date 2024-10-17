import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import BreweryListView from '../views/BreweryListView.vue'
import BreweryDetailsView from '../views/BreweryDetailsView.vue'
import BeerDetailsView from '../views/BeerDetailsView.vue'
import ReviewFormVue from '../views/ReviewFormView.vue'
import BeerFormView from '../views/BeerFormView.vue'
import TestingGroundView from '../views/TestingGroundView.vue'
import BeerListView from '../views/BeerListView.vue'
import BrewerModeView from '../views/BrewerModeView.vue'
import BreweryFormView from '../views/BreweryFormView.vue'
import EventListView from '../views/EventListView.vue'
import EventDetailsView from '../views/EventDetailsView.vue'
import EventFormView from '../views/EventFormView.vue'
import PageNotFoundView from '../views/PageNotFoundView.vue'


/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: LogoutView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
      meta: {
        requiresAuth: false
      }
    },
    {
        path: "/breweries",
        name: "breweryList",
        component: BreweryListView,
        meta: {
            requiresAuth: false
        }
    },
    {
        path: '/breweries/:id',
        name: "brewery",
        component: BreweryDetailsView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/beers/:id',
        name: 'beer',
        component: BeerDetailsView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/beers/:id/leave-review',
        name: 'reviewForm',
        component: ReviewFormVue,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/beers/form/:id',
        name: 'beerForm',
        component: BeerFormView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/beers',
        name: 'beers',
        component: BeerListView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/testing_ground',
        name: 'test',
        component: TestingGroundView,
        meta: {
            requiresAuth: false
        }
    },
    {
        path: '/brewer_mode',
        name: 'editBrewery',
        component: BrewerModeView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/breweries/form/:id',
        name: 'breweryForm',
        component: BreweryFormView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/events',
        name: 'eventList',
        component: EventListView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/events/:id',
        name: 'eventDetails',
        component: EventDetailsView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/events/form/:id',
        name: 'eventForm',
        component: EventFormView,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'pageNotFound',
        component: PageNotFoundView,
        meta: {
            requiresAuth: false
        }
    }
  ];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
