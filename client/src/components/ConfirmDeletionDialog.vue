<template>
    <dialog ref="confirmDelete">
        <p>Are you sure you want to delete {{ item }}?</p>
        <button>Yes</button>
        <button>Cancel</button>
    </dialog>
</template>

<script>
    export default {
        props: {
            item: {
                type: String,
                required: true
            }
        }
    }
</script>

<style scoped>

</style>