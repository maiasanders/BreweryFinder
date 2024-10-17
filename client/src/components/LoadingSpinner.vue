<!--
  LoadingSpinner component

  Shows or hides a "spinner" to show the user when the app is busy doing something, like making a call to the server.

  It has one "prop" called spin. True to show the spinner and false to hide it.

  To use this component on your page, suppose you have a data property called isLoading. 
  You set `isLoading = true` before issuing an asynchronous call (using axios for example). 
  And when the call completes, you set `isLoading = false`. 

  When you include the component, set the "spin" prop to the value of isLoading (or whatever your data property is). So
  to include this LoadingSpinner, add the component to your page like this:
  
    <loading-spinner id="spinner" v-bind:spin="isLoading" />

  When isLoading === true, the component will display, else it will be hidden (opacity = 0).
-->
<template>
  <font-awesome-icon
    v-bind:style="{ opacity: spin ? 1 : 0 }"
    icon="fa-solid fa-rotate"
    class="load-spinner fa-spin"
  />
</template>

<script>
export default {
  props: {
    spin: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style scoped>
.load-spinner {
  transition-property: opacity;
  transition-duration: 400ms;
}
</style>