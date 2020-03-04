<template>
  <v-row justify="center">
    <v-col sm="10">
      <v-row align="end">
        <v-col md="auto">
          <h2>Integrations</h2>
        </v-col>

        <v-col md="auto">
          <v-btn small text class="blue-grey--text pb-1 text-none" to="/integration_new">
            <v-icon>mdi-plus</v-icon>
            <span class="body-2 font-weight-bold">Add</span>
          </v-btn>
        </v-col>
      </v-row>

      <v-simple-table class="blue-grey darken-3">
        <template v-slot:default>
          <thead class="blue-grey darken-4">
            <tr>
              <th class="text-left">Kind</th>
              <th class="text-left">Name</th>
              <th class="text-left">Endpoints</th>
              <th class="text-left">Attr.</th>
              <th class="text-left">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.kind }}</td>
              <td>{{ item.name }}</td>
              <td>{{ item.endpoint }}</td>
              <td>{{ item.attributes }}</td>
              <td>
                <v-btn small icon class="blue-grey--text" disabled>
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear>
    </v-col>
  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        items: [],
        loading: false,
        form: {}
      }
    },
    computed: {
      'endpoints': function(){
        // https://github.com/vuejs/vue/issues/1964#issuecomment-162210972
        var r = global.realm || {}
        return {
          realm: '/companies/' + r.name + '/integrations',
          // projects_new: '/companies/' + r.name + '/projects/new',
          invalid: !r.name
        }
      }
    },
    watch: {
      'endpoints.realm': function(url){
        if(!this.endpoints.invalid)
          this.getItems()
      }
    },
    methods: {
      'getItems': function(){
        var vm = this
        var success = function(res){ vm.items = res.data.tools; vm.loading = false; }
        var url = this.endpoints.realm

        this.loading = true
        axios.get(url).then(success)
      }
    }
  }
</script>