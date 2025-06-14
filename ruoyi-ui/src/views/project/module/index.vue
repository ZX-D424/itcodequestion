<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模块名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入模块名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属菜单" prop="menuId">
        <el-select v-model="queryParams.menuId" placeholder="请选择所属菜单"  style="width: 200px">
          <el-option
              v-for="menu in menuDataList"
              :key="menu.id"
              :label="menu.name"
              :value="menu.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="禁用状态" prop="isDisabled">
        <el-select v-model="queryParams.isDisabled" placeholder="请选择禁用状态" clearable style="width: 200px">
          <el-option
            v-for="dict in disabled_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['project:module:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:module:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:module:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['project:module:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="moduleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="菜单名称" align="center" prop="menuName" />
      <el-table-column label="模块名称" align="center" prop="name" />
      <el-table-column label="排序值" align="center" prop="sortNum" />
      <el-table-column label="禁用状态" align="center" prop="isDisabled">
        <template #default="scope">
          <dict-tag :options="disabled_type" :value="scope.row.isDisabled"/>
        </template>
      </el-table-column>
      <el-table-column label="模块类型" align="center" prop="type">
        <template #default="scope">
          <dict-tag :options="module_type" :value="scope.row.type"/>
        </template>
      </el-table-column>

<!--      <el-table-column label="描述" align="center" prop="description" />-->
      <el-table-column label="图片链接" align="center" prop="imgUrl" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['project:module:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['project:module:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改项目模块对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="moduleRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模块名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入模块名称" />
        </el-form-item>

        <el-form-item label="所属菜单" prop="isDisabled">
          <el-select v-model="form.menuId" placeholder="请选择所属菜单" >
            <el-option
                v-for="menu in menuDataList"
                :key="menu.id"
                :label="menu.name"
                :value="menu.id"
            />
          </el-select>
        </el-form-item>


        <el-form-item label="禁用状态" prop="isDisabled">
          <el-radio-group v-model="form.isDisabled">
            <el-radio
              v-for="dict in disabled_type"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="模块类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio
                v-for="dict in module_type"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item label="排序值" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序值" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="图片链接" prop="imgUrl">
          <image-upload v-model="form.imgUrl"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Module">
import { listModule, getModule, delModule, addModule, updateModule } from "@/api/project/module"
import { getMenuDataList } from "@/api/project/menu.js"
const { proxy } = getCurrentInstance()
const { disabled_type } = proxy.useDict('disabled_type')
const { module_type } = proxy.useDict('module_type')
const moduleList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const menuDataList = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    isDisabled: null,
    menuId: null,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询项目模块列表 */
function getList() {
  loading.value = true
  listModule(queryParams.value).then(response => {
    moduleList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 查询项目模块列表 */
function initMenuDataList() {
  loading.value = true
  getMenuDataList().then(response => {
    menuDataList.value = response.data
    loading.value = false
  })
}


// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    name: null,
    menuId: null,
    isDisabled: null,
    sortNum: null,
    description: null,
    imgUrl: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    type: null,
  }
  proxy.resetForm("moduleRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加项目模块"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getModule(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改项目模块"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["moduleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateModule(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addModule(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除项目模块编号为"' + _ids + '"的数据项？').then(function() {
    return delModule(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('project/module/export', {
    ...queryParams.value
  }, `module_${new Date().getTime()}.xlsx`)
}

getList();
initMenuDataList();
</script>
