<template>
<div id="cart-app" v-on:resize="$store.commit('UPDATE_WIN_WIDTH', winWid)">
    <header>
        <img src="../public/logo.svg" id="header-logo" />
        <h2 id="site-title">BrewFinder</h2>
        <!-- TODO: Add in user pic here? -->
        <font-awesome-icon 
            id="brewer-mode" 
            icon="fa-solid fa-beer-mug-empty" 
            v-if="brewerMode"
            @click="$router.push({name: 'editBrewery'})"/>
        <font-awesome-icon icon="bars" id="open-nav-mob" @click="$store.commit('TOGGLE_NAV')"/>
    </header>
    <nav id="site-nav" v-show=" !$store.getters.isMobile || !$store.state.hideNav">
        <router-link :to="{ name: 'home' }">Home</router-link>
        <router-link :to="{ name: 'breweryList'}">Breweries</router-link>
        <router-link :to="{name: 'beers'}">Beers</router-link>
        <RouterLink :to="{name: 'eventList'}">Events</RouterLink>
        <router-link class="login-out" v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
            Logout
        </router-link >
        <router-link class="login-out" v-bind:to="{ name: 'login' }" v-else>Login</router-link>
    </nav>
    <main>
        <router-view />
    </main>
    <footer>
        <p>&copy; 2024 BrewFinder, Maia Sanders</p>
    </footer>
</div>
</template>

<script>
export default {
    data() {
        return {
            hideNav: true,
            winWid: window.innerWidth,
            winHeight: window.innerHeight
        }
    },
    computed: {
        brewerMode() {
            if (this.$store.state.token) {
                return this.$store.state.user.authoritiesString.includes('BREWER')
            } else {
                return false;
            }
        }
    },
    methods: {
        toggleMobNav() {
            if (this.winWidth <= 425) this.hideNav = !this.hideNav
        },
        setWinSize() {
            this.winWid = window.innerWidth,
            this.$store.commit('SET_WIN_WID', this.winWid)
            this.$store.commit('SET_WIN_HEIGHT', window.innerHeight)
        }
    },
    mounted() {
        window.addEventListener('resize', this.setWinSize)
    }
}
</script>

<style scoped>
/* Header styling */

header {
    background-color: #B5B185;
    display: flex;
    font-family: "Antonio", sans-serif;
    gap: 1em;
    justify-content: flex-start;
    align-items: center;
    padding: 0 1em 0 1em;
    width: 100%;
    height: 80px;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 10;
    box-sizing: border-box;
}

h2 {
    font-size: 2rem;
}

#header-logo {
    width: 60px;
    object-fit: contain;
}

#brewer-mode {
        font-size: 3rem;
        margin-left: auto;
    }

#open-nav-mob {
    display: none;
}

/* Nav styling */

#site-nav {
    background-color: #D8D5AA;
    position: fixed;
    top: 80px;
    left: 0;
    width: 100%;
    height: 30px;
    z-index: 10;
    box-sizing: border-box;
    border-bottom: 1px solid #b5b185;
    display: flex;
    padding: 10px;
    justify-content: start;
    align-items: center;
    gap: 1.5em;
}

.login-out {
    margin-left: auto;
}

/* RouterView adjustment */



/* Footer Styling */

footer {
    background-color: #D8D5AA;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    padding-left: 1em;
    height: 25px;
    border-top: 1px solid #b5b185;
    z-index: 10;
}

footer>p {
    padding: 0;
    margin: .25em;
}

@media only screen and (max-width: 425px) {
    /* Header styling */
    header {
        justify-content: space-between;
        height: 40px
    }
    #site-title {
        display: none;
    }
    #open-nav-mob {
        display: block;
    }
    #header-logo {
        width: 35px;
    }
    #brewer-mode {
        font-size: 2rem;
    }
    

    main {
        margin-top: 50px;
    }

    /* Nav styling */
    #site-nav {
        top: 40px;
        flex-direction: column;
        align-items: center;
        height: auto;
        width: auto;
        border-radius: 0 0 10px 10px;
        left: 25%;
        right: 25%;
        padding-bottom: 1rem;
    }
    .login-out {
        margin-left: 0;
    }
    #site-nav::backdrop {
        background-color: #D8D5AA;
    }
    .hidden {
        display: none;
    }
}
</style>
