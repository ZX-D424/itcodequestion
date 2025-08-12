// import { defineStore } from 'pinia';


// const useDictStore = defineStore(
//   'dict',
//   {
//     state: () => ({
//       dict: new Array()
//     }),
//     actions: {
//       // 获取字典
//       getDict(_key) {
//         if (_key == null && _key == "") {
//           return null
//         }
//         try {
//           for (let i = 0; i < this.dict.length; i++) {
//             if (this.dict[i].key == _key) {
//               return this.dict[i].value
//             }
//           }
//         } catch (e) {
//           return null
//         }
//       },
//       // 设置字典
//       setDict(_key, value) {
//         if (_key !== null && _key !== "") {
//           this.dict.push({
//             key: _key,
//             value: value
//           })
//         }
//       },
//       // 删除字典
//       removeDict(_key) {
//         var bln = false
//         try {
//           for (let i = 0; i < this.dict.length; i++) {
//             if (this.dict[i].key == _key) {
//               this.dict.splice(i, 1)
//               return true
//             }
//           }
//         } catch (e) {
//           bln = false
//         }
//         return bln
//       },
//       // 清空字典
//       cleanDict() {
//         this.dict = new Array()
//       },
//       // 初始字典
//       initDict() {
//       }
//     }
//   })

// export default useDictStore



// src/store/modules/dict.js
import { defineStore } from 'pinia';
import { ref } from 'vue'; // 引入 ref

const useDictStore = defineStore(
  'dict',
  {
    state: () => ({
      dict: [] // 使用 ref 创建响应式引用

    }),
    actions: {
      // 获取字典
      getDict(_key) {
        if (_key == null && _key == "") {
          return null;
        }
        try {
          for (let i = 0; i < this.dict.value.length; i++) { // 访问 ref 的 value 属性
            if (this.dict.value[i].key == _key) {
              return this.dict.value[i].value;
            }
          }
        } catch (e) {
          return null;
        }
      },
      // 设置字典
      setDict(_key, value) {
        if (_key !== null && _key !== "") {
          this.dict.value.push({ // 访问 ref 的 value 属性
            key: _key,
            value: value
          });
        }
      },
      // 删除字典
      removeDict(_key) {
        let bln = false;
        try {
          for (let i = 0; i < this.dict.value.length; i++) { // 访问 ref 的 value 属性
            if (this.dict.value[i].key == _key) {
              this.dict.value.splice(i, 1); // 访问 ref 的 value 属性
              return true;
            }
          }
        } catch (e) {
          bln = false;
        }
        return bln;
      },
      // 清空字典
      cleanDict() {
        this.dict.value = []; // 访问 ref 的 value 属性
      },
      // 初始字典
      initDict() {
      }
    }
  }
);

export default useDictStore;