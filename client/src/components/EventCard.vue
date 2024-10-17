<template>
    <div class="event-card card">
        <h3>{{ event.eventName }}</h3>
        <img :src="event.imgUrl" >
        <p>{{ event.breweryName }}</p>
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