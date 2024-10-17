<template>
<div id="hero">
    <div id="hero-text">
        <h3>Featured Brewery</h3>
        <h1>{{ brewery.breweryName }}</h1>
    </div>
    <img :src="brewery.picUrl" />
</div>
</template>

<script>
export default {
    props: {
        'brewery': {
            type: Object,
            required: true
        }
    }
}
</script>

<style scoped>

#hero-text {
    height: 400px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: space-between;
    width: 100%;
}

h3 {
    position: absolute;
    left: 20px;
    top: 180px;
    z-index: 2;
}

h1 {
    position: absolute;
    z-index: 1;
    bottom: 10px;
    left: 20px;
}

img {
    width: 98%;
    height: 400px;
    object-fit: cover;
    position: absolute;
    top: 180px;
    left: 1%;
    border-radius: 5px;
    box-shadow: 0 0 3px var(--input-border-color);
}

img:hover {
    border: 1px solid rgba(255, 255, 255, .8);
    box-shadow: 1px 1px 3px var(--input-border-color);
}

#hero:hover #hero-text {
    text-shadow: 0 0 1px white;
}

/* Mobile styles */

@media only screen and (max-width: 425px) {
    h1 {
        bottom: 30%;
        width: min-content;
    }
    img {
        width: 95%;
        height: 60%;
        object-fit: cover;
        top: 90px;
    }
    h3 {
        top: 90px
    }
}
</style>