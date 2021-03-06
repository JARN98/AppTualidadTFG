import mongoose, { Schema } from 'mongoose'
import { Photo } from '../photo'
import { Comentario } from '../comentario'
import { User } from '../user'
const S = require('string')

const noticiaSchema = new Schema({
  title: {
    type: String
  },
  description: {
    type: String
  },
  likes: {
    type: String,
    default: '0'
  },
  comentarios: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Comentario'
  }],
  photos: [{
    id: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Photo'
    },
    link: {
      type: String
    }
  }],
  autor: {
    email: {
      type: String
    },
    picture: {
      type: String
    },
    name: {
      type: String
    },
    id: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
    }
  },
  localizacion: {
    type: [Number],
    required: true,
    get: (v) => (v && v.length > 0) ? v.join() : null,
    set: (v) => (S(v).isEmpty()) ? null : v.split(',').map(Number),
  },
  direccion: {
    type: String
  }
}, {
  strict: false,
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

noticiaSchema.pre('remove', { query: true }, function (next) {
  Photo.find({ noticia: this.id })
    // eslint-disable-next-line handle-callback-err
    .exec((err, result) => {
      Promise.all(result.map(photo => photo.remove()))
      next()
    })

  Comentario.find({ noticia: this.id })
    // eslint-disable-next-line handle-callback-err
    .exec((err, result) => {
      Promise.all(result.map(comentario => comentario.remove()))
      next()
    })

})

noticiaSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      title: this.title,
      likes: this.likes,
      localizacion: this.localizacion,
      photos: this.photos[0]
    }

    const viewFull = {
      id: this.id,
      title: this.title,
      description: this.description,
      likes: this.likes,
      comentarios: this.comentarios,
      localizacion: this.localizacion,
      photos: this.photos,
      autor: this.autor,
      direccion: this.direccion,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...viewFull
    } : view
  }
}

noticiaSchema.index({localizacion: '2dsphere'})

const model = mongoose.model('Noticia', noticiaSchema)

export const schema = model.schema
export default model
