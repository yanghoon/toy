<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2>Projects</h2>

      <v-row>
        <v-col sm="3" class="blue-grey--text text--darken-3">
          <v-text-field dense
              label="+ Add Project"
              hint="Press enter to create"
              v-model="form.newProjName"
              :disabled="endpoints.invalid"
              @keyup.enter="addProj">
          </v-text-field>
        </v-col>
      </v-row>

      <v-simple-table class="blue-grey darken-3">
        <template v-slot:default>
          <thead class="blue-grey darken-4">
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Groups</th>
              <th class="text-left">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.subgroups.length }}</td>
              <td>
                <v-btn small icon class="blue-grey--text" @click="removeProj(item)">
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
        form: {
          newProjName: ''
        }
      }
    },
    computed: {
      'endpoints': function(){
        // https://github.com/vuejs/vue/issues/1964#issuecomment-162210972
        var r = global.realm || {}
        return {
          projects: '/companies/' + r.name + '/projects',
          projects_new: '/companies/' + r.name + '/projects/new',
          invalid: !r.name
        }
      }
    },
    watch: {
      'endpoints.projects': function(url){
        if(!this.endpoints.invalid)
          this.getProjList()
      }
    },
    methods: {
      'getProjList': function(){
        var vm = this
        var success = function(res){ vm.items = res.data; vm.loading = false; }
        var url = this.endpoints.projects

        this.loading = true
        axios.get(url).then(success)
      },
      'addProj': function(){
        var url = this.endpoints.projects_new + '?project=' + this.form.newProjName

        this.form.newProjName = ''
        axios.post(url).then(this.getProjList)
      },
      'removeProj': function(item){
        var url = this.endpoints.projects + '/' + item.id

        axios.delete(url).then(this.getProjList)
      }
    }
  }
</script>