import { createStore as _createStore } from "vuex";
import axios from "axios";

import CategoryService from "../services/CategoryService";
import EventService from "../services/EventService";
import { axiosErrorHandling } from "../services/ServiceUtils";

const MAX_MOBILE_WID_PX = 425;

export function createStore(currentToken, currentUser) {
    let store = _createStore({
        state: {
            // Authentication info
            token: currentToken || "",
            user: currentUser || {},

            // Display variables
            winWidPx: window.innerWidth,
            winHeightPx: window.innerHeight,
            hideNav: true,
            hideFilters: true,
            winWidth: window.innerWidth,
            
            // Filters for lists
            beerFilters: {
                query: "",
                minAbv: null,
                maxAbv: null,
            },
            breweryFilters: {
                city: "",
                state: "",
                kidFriendly: null,
                dogFriendly: null,
                servesFood: null,
            },
            eventFilter: {
                // city: "",
                // state: "",
                minDate: null,
                maxDate: null,
                over21: null,
            },
            states: [
                "AL",
                "AK",
                "AS",
                "AZ",
                "AR",
                "CA",
                "CO",
                "CT",
                "DE",
                "DC",
                "FM",
                "FL",
                "GA",
                "GU",
                "HI",
                "ID",
                "IL",
                "IN",
                "IA",
                "KS",
                "KY",
                "LA",
                "ME",
                "MH",
                "MD",
                "MA",
                "MI",
                "MN",
                "MS",
                "MO",
                "MT",
                "NE",
                "NV",
                "NH",
                "NJ",
                "NM",
                "NY",
                "NC",
                "ND",
                "MP",
                "OH",
                "OK",
                "OR",
                "PW",
                "PA",
                "PR",
                "RI",
                "SC",
                "SD",
                "TN",
                "TX",
                "UT",
                "VT",
                "VI",
                "VA",
                "WA",
                "WV",
                "WI",
                "WY",
            ],

            // API call results
            _categories: [],
            _events: [],

            // Form fields for cross-component communication
            imgUploadUrl: "",
            selectedCategoriesArr: []
        },
        mutations: {
            SET_AUTH_TOKEN(state, token) {
                state.token = token;
                localStorage.setItem("token", token);
                axios.defaults.headers.common[ "Authorization" ] = `Bearer ${token}`;
            },
            SET_USER(state, user) {
                state.user = user;
                localStorage.setItem("user", JSON.stringify(user));
            },
            LOGOUT(state) {
                localStorage.removeItem("token");
                localStorage.removeItem("user");
                state.token = "";
                state.user = {};
                axios.defaults.headers.common = {};
            },
            // Controls for show/hide elements that are hidden in mobile
            SET_WIN_WID(state, newWid) {
                state.winWidth = newWid
            },
            TOGGLE_NAV(state) {
                state.hideNav = !state.hideNav;
            },
            HIDE_NAV(state) {
                state.hideNav = true;
            },
            SHOW_NAV(state) {
                state.hideNav = false;
            },
            TOGGLE_FILTERS(state) {
                state.hideFilters = !state.hideFilters;
            },
            HIDE_FILTERS(state) {
                state.hideFilters = true;
            },
            SHOW_FILTERS(state) {
                state.hideFilters = false;
            },
            UPDATE_WIN_WIDTH(state, width) {
                state.winWidth = width;
            },
            SET_WIN_HEIGHT(state, height) {
                state.winHeightPx = height
            },
            
            // Set and reset beer filters
            UPDATE_BEER_FILTERS(state, filters) {
                if (filters.maxAbv === '') filters.maxAbv = null;
                state.beerFilters = filters;
            },
            CLEAR_BEER_FILTERS(state) {
                state.beerFilters = {
                    query: "",
                    minAbv: null,
                    maxAbv: null
                };
            },
            // Set and clear filters for breweries
            SET_BREWERY_FILTERS(state, filters) {
                state.breweryFilters = filters;
            },
            CLEAR_BREWERY_FILTERS(state) {
                state.breweryFilters = {
                    city: "",
                    state: "",
                    kidFriendly: null,
                    dogFriendly: null,
                    servesFood: null,
                };
            },
            // Set/reset event filters
            SET_EVENT_FILTERS(state, filters) {
                state.eventFilter = filters;
            },
            CLEAR_EVENT_FILTERS(state) {
                state.eventFilter = {
                    city: "",
                    state: "",
                    minDate: "",
                    maxDate: "",
                    over21: null,
                }
            },

            // Handle dorm field variables
            UPDATE_IMG_URL(state, url) {
                state.imgUploadUrl = url;
            },
            CLEAR_IMG_URL(state) {
                state.imgUploadUrl = "";
            },
            UPDATE_SELECTED_CATEGORIES(state, categories) {
                state.selectedCategoriesArr = categories;
            }
            
        },
        getters: {
            isMobile(state) {
                return state.winWidth <= MAX_MOBILE_WID_PX;
            },
            getCategories(state) {
                return state._categories;
            },
            getSelectedCategories(state) {
                return state.selectedCategoriesArr;
            },
            getEventsByBrewery(state) {
                return state._events;
            }
        },
        actions: {
            // Categories API calls
            fetchCategories(context) {
                CategoryService.getCategories()
                    .then(response => {
                        if (response.status === 200) {
                            context.state._categories = response.data
                        }
                        return response
                    })
                    .catch(error => axiosErrorHandling(error))
            },
            addNewCategory(context, category) {
                CategoryService.addCategory(category)
                    .then(response => {
                            const newCat = response.data
                            context.state._categories.push(newCat)
                            context.state.selectedCategoriesArr.push(newCat)
                        return response;
                    })
                    .catch(error => axiosErrorHandling(error))
            },
            // Events API calls
            getEventsByBrewery(context, id) {
                EventService.getEventsByBrewery(id)
                    .then(response => {
                        if (response.status === 200) {
                            context.state._events = response.data
                        }
                        return response
                    })
            }
        }
    });
    return store;
}
