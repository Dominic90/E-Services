class Feed < ActiveRecord::Base
  belongs_to :event
  belongs_to :user
  belongs_to :connection
  has_many :streams
end
