<template>
  <h1>Logout</h1>
</template>

<script>
export default {
  created() {
    this.$store.commit('HIDE_NAV')
    this.$store.commit("LOGOUT");
    this.$router.push("/login");
  }
};
</script>
