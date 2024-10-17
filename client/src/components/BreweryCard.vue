<template>
<div class="brewery-card card">
    <h3>{{ brewery.breweryName }}</h3>
    <img :src="brewery.picUrl" />
    <p>{{ brewery.city }}, {{ brewery.state }}</p>
</div>
</template>

<script>
export default {
    props: ['brewery']
}
</script>

<style scoped>
.brewery-card {
    border: 1px solid #B5B185;
    border-radius: 10px;
    padding: 10px;
    background-color: #f2f1e9;
    box-shadow: 2px 2px 2px #00000030;
    width: 260px;
    display: grid;
}
.brewery-card:hover {
    background-color: #f6f5ef;
    box-shadow: 2px 2px 2px #00000062;
}
img {
    /* max-width: 250px; */
    max-height: 166px;
    object-fit: cover;
    justify-self: center;
}

</style>