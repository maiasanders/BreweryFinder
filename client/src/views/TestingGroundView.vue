<template>
    <div id="testing-ground" @imgUploaded="imgUrl = $store.state.imgUploaded">
        <ImageUploader />
        <div v-if="imgUrl">
            <img :src="imgUrl" >
        </div>
    </div>
</template>

<script>
import ImageUploader from '../components/ImageUploader.vue';

export default {
    components: { ImageUploader },
    data() {
        return {
            imgUrl: null
        }
    }
}
</script>