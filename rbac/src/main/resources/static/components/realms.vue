<template>
  <v-row justify="center">
    <v-col sm="10">
      <div>
        {{ selected }}
      </div>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Calories</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in desserts" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.calories }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-col>
  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        desserts: [
          { name: 'Frozen Yogurt', calories: 159 },
          { name: 'Ice cream sandwich', calories: 237 },
          { name: 'Eclair', calories: 262 },
          { name: 'Cupcake', calories: 305 },
          { name: 'Gingerbread', calories: 356 },
          { name: 'Jelly bean', calories: 375 },
          { name: 'Lollipop', calories: 392 },
          { name: 'Honeycomb', calories: 408 },
          { name: 'Donut', calories: 452 },
          { name: 'KitKat', calories: 518 },
        ]
      }
    },
  }
</script>