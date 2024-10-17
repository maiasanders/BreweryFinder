<template>
<div id="event-details">
    <h1>{{ event.eventName }}</h1>
    <h4>{{ formattedDate }} {{ event.begins }} - {{ event.ends }}</h4>
    <h3>{{ event.breweryName }} <span v-if="event.is21Up" class="over-21"> 21 + </span> </h3>
    <p>{{ event.desc }}</p>
    <div id="categories">
        <div class="category-tag" v-for="category in event.categories" :key="category.id">{{ category.categoryName }}</div>
    </div>
    
</div>
</template>

<script>
import EventService from '../services/EventService';
import { axiosErrorHandling } from '../services/ServiceUtils';

export default {
    data() {
        return {
            event: {}
        }
    },
    computed: {
        formattedDate() {
            let date = new Date(this.event.eventDate);
            return `${date.getMonth() + 1} / ${date.getDate()} / ${date.getFullYear()}`;
        }
    },
    created() {
        EventService.getEventById(this.$route.params.id)
            .then(response => {
                this.event = response.data
            })
            .catch(error => axiosErrorHandling(error))
    }
}
</script>

<style scoped>
#event-details {
    display: grid;
    grid-template-columns: 2fr 1fr;
    grid-template-areas: "name date"
                        "brewery brewery"
                        "desc desc"
                        "cats cats";
    padding: 10px;
    gap: 20px;
}
h1 {
    grid-area: name;
}
h4 {
    grid-area: date;
    text-align: end;
}
h3 {
    grid-area: brewery;
}
.over-21 {
    color: red;
}
p {
    grid-area: desc;
}
#categories {
    grid-area: cats;
    display: flex;
    flex-wrap: wrap;
    gap: 50px;
}
.category-tag {
    border: 1px solid var(--input-border-color);
    border-radius: 10px;
    padding: 8px;
    background-color: var(--nav-color);
}

@media only screen and (max-width: 425px) {
    #event-details {
        grid-template-columns: 1fr;
        grid-template-areas: "name" "brewery" "date" "desc" "cats";
        justify-content: center;
    }
    h4 {
        text-align: center;
    }
}
</style>