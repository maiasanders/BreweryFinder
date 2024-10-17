<template>
    <div id="beer-info">
        <h1>{{ beer.beerName }}</h1>
        <h3 id="brewery-name">{{ beer.breweryName }}</h3>
        <h3 id="style-name">{{ beer.styleName }}</h3>
        <h4 id="abv">{{ beer.abv }} ABV</h4>
        <h4 id="season" v-show="beer.seasonal">{{ beer.seasonName }} Only</h4>
        <p>{{ beer.desc }}</p>
    </div>
</template>

<script>
export default {
    props: {
        beer: {
            type: Object,
            required: true
        }
    }
}
</script>

<style scoped>
#beer-info {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "name name"
                        "brew style"
                        "abv season"
                        "desc desc";
}

h1 {
    grid-area: name;
}
#brewery-name {
    grid-area: brew;
}
#style-name {
    grid-area: style;
    text-align: right;
}
#abv {
    grid-area: abv;
}
#season {
    grid-area: season;
    text-align: right;
}
p {
    grid-area: desc;
}
</style>