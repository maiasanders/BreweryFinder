<template>
    <div class="featured-event">
        <img :src="event.imgUrl" />
        <p>{{ event.eventName }}</p>
    </div>
</template>

<script>
export default {
    props: {
        event: {
            type: Object,
            required: true
        }
    }
}
</script>

<style scoped>
#featured-event {
    height: 100px;

}
img {
    object-fit: cover;
    height: 100px;
    width: 100%;
    border-radius: 5px;
    box-shadow: 0 0 3px var(--input-border-color);
}
p {
    position: relative;
    top: -100px;
    left: 2px;
    z-index: 2;
    width: 99%;
    display: flex;
    align-items: flex-end;
    height: 100px;
    margin: 0;
}
.featured-event:hover img {
    border: 1px solid rgba(255, 255, 255, .8);
    box-shadow: 1px 1px 3px var(--input-border-color);
}
.featured-event:hover p {
    text-shadow: 0 0 1px rgb(211, 197, 197);
}

@media only screen and (max-width: 425px) {
    p {
        top: -25px
    }
    img {
    height: 90%;
    width: 45%;
    position: absolute;
    }
}
</style>