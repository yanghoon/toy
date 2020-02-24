<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2 class="py-3">Configuration</h2>
      <div class="blue-grey--text">for realm '{{ realm }}'</div>

      <v-form v-if="config.links" class="pt-8">
        <h3>Links</h3>
        <template v-for="(link, name) in config.links">
          <v-text-field :label="name" :key="name" :value="link" disabled></v-text-field>
        </template>
      </v-form>

      <v-form v-if="config.authorization" class="pt-8">
        <h3>Authorization Server</h3>
        <template v-for="(prop, name) in config.authorization">

          <v-text-field :label="name" :key="name" :value="prop" disabled
            v-if="typeof prop !== 'object'">
          </v-text-field>

          <template v-if="typeof prop == 'object'">
            <h4 :key="name" class="text-capitalize">{{ name }}</h4>
            <v-text-field v-for="(v, k) in prop" :label="k" :key="k" :value="v" disabled></template>
          </template>

        </template>
      </v-form>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear>

    </v-col>
  </v-row>
</template>

<script>
// https://hoorooroob.tistory.com/entry/React-React-Naive-TIPS-axios-%EC%99%80-fetch-%EC%96%B4%EB%96%A4-%EA%B2%83%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%A0%EA%B9%8C
// https://ux.stories.pe.kr/138
module.exports = {
    data: function() {
      return {
        config: {
          links: {
            grafana: 'http://grafana.local:8080/login/generic_oauth',
            jenkins: 'http://jenkins.local:8080'
          }
        },
        realm: 'dummy',
        loading: true
      }
    },
    beforeMount: function() {
      var vm = this
      var success = function(res){ vm.config = res.data; vm.loading = false; }
      var url = '/companies/' + this.realm + '/config'

      this.loading = true
      axios.get(url).then(success)
    }
  }
</script>