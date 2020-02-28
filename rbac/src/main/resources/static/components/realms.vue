<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2>Realms</h2>

      <v-row>
        <v-col sm="3" class="blue-grey--text text--darken-3">
          <v-text-field dense
            label="+ Add Realm"
            hint="Press enter to create"
            v-model="form.newRealmName"
            @keyup.enter="addRealm">
          </v-text-field>
        </v-col>
      </v-row>

      <v-simple-table class="blue-grey darken-3">
        <template v-slot:default>
          <thead class="blue-grey darken-4">
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Enabled</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.name }}</td>
              <td>
                <v-switch dense v-model="item.enabled" @change="changeRealm(item)"></v-switch>
                <!-- {{ item.enabled }} -->
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
        loading: true,
        form: {
          newRealmName: ''
        }
      }
    },
    beforeMount: function() {
      this.getRealmList();
    },
    methods: {
      'getRealmList': function(){
        var vm = this
        var success = function(res){ vm.items = res.data; vm.loading = false; }

        this.loading = true
        axios.get('/companies').then(success)
      },
      'addRealm': function(){
        var url = '/companies/new?realm=' + this.form.newRealmName;

        this.form.newRealmName = ''
        axios.post(url).then(this.getRealmList)
      },
      'changeRealm': function(item){
        var url = '/companies/' + item.name + '?enabled=' + item.enabled;
        axios.put(url).then(this.getRealmList)
      }
    }
  }
</script>