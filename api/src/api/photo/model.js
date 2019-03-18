import mongoose, { Schema } from 'mongoose'
import { Noticia } from '../noticia'
import { success } from '../../services/response'
const uploadService = require('../../services/upload')

const photoSchema = new Schema({
  noticia: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Noticia'
  },
  imgurLink: {
    type: String
  },
  deleteHash: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})
photoSchema.pre('remove', { query: true }, function (next) {
  uploadService.deleteImage(this.deletehash)
  return next()
})

photoSchema.post('remove', { query: true }, function (res, next) {
  Noticia.findOne({ 'photos.link': res.imgurLink })
    .then(noticia => {
      var i = 0
      var encontrado = false
      while (!encontrado && i < noticia.photos.length) {
        var element = noticia.photos[i]
        if (element.id.equals(this.id)) {
          noticia.photos.splice(i, 1)
          encontrado = true
        }
        i++
      }

      noticia.save()
    })

  next()
})

photoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      noticia: this.noticia,
      imgurLink: this.imgurLink,
      deleteHash: this.deleteHash,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Photo', photoSchema)

export const schema = model.schema
export default model
