<template>
    <div id="image-uploader">
        <button @click="upload">Upload Image</button>
        <div v-if="imgUrl && imgInfo">
            <img :src="imgUrl">
            <button @click="acceptImg">Accept Image{{ isAccepted ? "  ✅" : "" }}</button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'cloudinary',
    data() {
        return {
            widget: {},
            imgInfo: null,
            imgUrl: null,
            isAccepted: false
        }
    },
    methods: {
        upload() {
            this.widget.open();

        },
        acceptImg() {
            this.$store.commit('UPDATE_IMG_URL', this.imgUrl);
            this.isAccepted = true;
        }
    },
    created() {
        this.widget = window.cloudinary.createUploadWidget(
            {
                cloudName: 'dlwzfvixi',
                uploadPreset: 'brewery-finder-app'
            },
            (error, result) => {
                if (!error && result && result.event === 'success') {
                    this.imgInfo = result.info;
                    this.imgUrl = result.info.url;
                }
            }
        )
    }
}
</script>

<style scoped>
img {
    max-width: 400px;
    max-height: 400px;
}
#image-uploader {
    padding: 10px;
    max-width: 80%;
    border: 1px solid var(--input-border-color);
    border-radius: 10px;
}
</style>