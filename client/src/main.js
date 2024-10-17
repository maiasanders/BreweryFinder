import './assets/main.css'

import { createApp } from 'vue'
import MyApp from './App.vue'
import { createStore } from './store'
import router from './router'
import axios from 'axios'

/* import fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faCartPlus, faTrashCan, faXmark, faMagnifyingGlass, faRotate, faTable, faGrip, faStar as solidStar, faPlus, faPenToSquare, faBars, faFilter, faBeerMugEmpty }
  from '@fortawesome/free-solid-svg-icons'
import { faComment, faStar, faStarHalfStroke, faThumbsUp, faThumbsDown, faCircleXmark } from '@fortawesome/free-regular-svg-icons'

/* add icons to the library */
library.add(faCartPlus);
library.add(faTrashCan);
library.add(faXmark);
library.add(faMagnifyingGlass);
library.add(faRotate);
library.add(faTable);
library.add(faGrip);
library.add(faComment);
library.add(faStar);
library.add(faStarHalfStroke);
library.add(faThumbsUp);
library.add(faThumbsDown);
library.add(solidStar);
library.add(faPlus);
library.add(faPenToSquare);
library.add(faBars);
library.add(faFilter);
library.add(faBeerMugEmpty);
library.add(faCircleXmark)

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;

/*
 * The authorization header is set for axios when you login but what happens when
 * you come back or the page is refreshed. When that happens you need to check
 * for the token in local storage and if it exists you should set the header
 * so that it will be attached to each request.
 */
let currentToken = localStorage.getItem('token')
let currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken) {
  // Set token axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

// Create the Vuex store passing in the stored credentials
const store = createStore(currentToken, currentUser);

const app = createApp(MyApp);
app.use(store);
app.use(router);
app.component('font-awesome-icon', FontAwesomeIcon);
app.mount('#app');

