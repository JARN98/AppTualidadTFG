import mongoose, { Schema } from 'mongoose'
import { Photo } from '../photo'
import { Comentario } from '../comentario'

const noticiaSchema = new Schema({
  title: {
    type: String
  },
  description: {
    type: String
  },
  likes: {
    type: String
  },
  comentarios: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Comentario'
  }],
  localizacion: {
    type: String
  },
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
  }
}, {
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

//  Comentario.find({ noticia: this.id })
//     // eslint-disable-next-line handle-callback-err
//     .exec((err, result) => {
//       Promise.all(result.map(photo => photo.remove()))
//       next()
//     })
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
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...viewFull
    } : view
  }
}

const model = mongoose.model('Noticia', noticiaSchema)

export const schema = model.schema
export default model
