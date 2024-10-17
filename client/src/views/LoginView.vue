<template>
  <div id="login">
    <form v-on:submit.prevent="login">
      <h1>Please Sign In</h1>
      <div id="fields">
        <label for="username">Username </label>
        <input
          type="text"
          id="username"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <div>&nbsp;</div>
        <label for="password">Password </label>
        <input
          type="password"
          id="password"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <div>&nbsp;</div>
        <div><button type="submit">Sign in</button></div>
      </div>
      <hr/>
      Need an account? <router-link v-bind:to="{ name: 'register' }">Register!</router-link>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;
          if (!response) {
            alert(error);
          } else if (response.status === 401) {
            alert("Invalid username and password!");
          } else {
            alert(response.message);
          }
        });
    },
  },
  created() {
    this.$store.commit('HIDE_NAV')
  }
};
</script>

<style scoped>
#login {
    display: flex;
    justify-content: center;
}
form {
    display: flex;
    flex-direction: column;
    align-items: center;
    border: solid 1px #b5b185;
    width: fit-content;
    border-radius: 10px;
    padding: 20px;
    margin: 10px
}
div:has(> button) {
    display: flex;
    justify-content: center;
}
</style>
